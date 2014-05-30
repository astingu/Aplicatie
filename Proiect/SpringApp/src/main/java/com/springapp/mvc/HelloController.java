package com.springapp.mvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.springapp.mainservice.MainService;
import com.springapp.model.MoviesDetails;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController{

    /* lista ce contine rezultatele extrase din cele doua servere */
    List<MoviesDetails> moviesDetails = new ArrayList<MoviesDetails>();

    ApplicationContext context;

    MainService mainService;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

        moviesDetails.clear();

        context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");

        mainService = (MainService)context.getBean("MainService");

		model.addAttribute("message", "Hello world!");
		return "hello";
	}

    @RequestMapping(value="actionPost", method = RequestMethod.POST)
    public String doPost( @RequestParam("movie")String movieName, ModelMap newModel)
            throws JsonParseException, JsonMappingException,IOException {
        try{

            List<MoviesDetails> allRecords = new ArrayList<MoviesDetails>();

            /* lista cu toate inregistrarile din baza de date */
            allRecords = mainService.find();

            /* se sterg toate inregistrarile din baza de date care au depasit 15 minute */
            deleteFromDatabase(allRecords);

              /* cautare in baza de date dupa cuvantul cheie introdus */
            moviesDetails = mainService.findMovieByName(movieName);


            for(int i = 0; i < allRecords.size(); i++){
                System.out.println(allRecords.get(i).getTitle());
            }

            /* daca s-au gasit rezultate in baza de date, acestea vor fi salvate in lista moviesDetails si nu
             * se va mai face cautare pe servere */

             if(!moviesDetails.isEmpty()){

                        System.out.println("S-a gasit in baza de date");

                        newModel.addAttribute("moviesDetails", moviesDetails);

            }

            /* daca nu se gaseste nicio inregistrare se realizeaza cautarea pe servere */
            if(moviesDetails.isEmpty()){

                System.out.println("Cautare pe server");

                String server_urlSync = "http://localhost:8081/tracktv?query=" + movieName;

                String server_urlPool = "http://localhost:8082/movies/" + movieName;

                SyncController syncController = new SyncController(server_urlSync);
                PoolController poolController = new PoolController(server_urlPool);

                Thread thread1 = new Thread(syncController);
                Thread thread2 = new Thread(poolController);

                thread1.start();
                thread2.start();

                thread1.join();
                thread2.join();

                /* se face merge intre rezultatele intoarse de pe cele doua servere */
                for(int i = 0; i < syncController.moviesDetails.size(); i++){

                            int verified = 0;

                            for(int j = 0; j < poolController.moviesDetails.size(); j++){

                                /* cazul in care filmul se gaseste pe ambele servere */
                                if(syncController.moviesDetails.get(i).getTitle().equals(poolController.moviesDetails.get(j).getTitle())){

                                    MoviesDetails newObject = new MoviesDetails();

                                    newObject.setTitle(syncController.moviesDetails.get(i).getTitle());
                                    newObject.setYear(syncController.moviesDetails.get(i).getYear());
                                    newObject.setUrl(syncController.moviesDetails.get(i).getUrl());
                                    newObject.setPoster(syncController.moviesDetails.get(i).getPoster());
                                    newObject.setGenre(poolController.moviesDetails.get(j).getGenre());
                                    newObject.setActors(poolController.moviesDetails.get(j).getActors());
                                    newObject.setPlot(poolController.moviesDetails.get(j).getPlot());
                                    newObject.setCountry(poolController.moviesDetails.get(j).getCountry());

                                    moviesDetails.add(newObject);

                                    verified = 1;
                        }
                    }

                    /* cazul in care filmul se gaseste doar pe serverul SyncWebServer */
                    if(verified == 0){

                        MoviesDetails newObject = new MoviesDetails();

                        newObject.setTitle(syncController.moviesDetails.get(i).getTitle());
                        newObject.setYear(syncController.moviesDetails.get(i).getYear());
                        newObject.setUrl(syncController.moviesDetails.get(i).getUrl());
                        newObject.setPoster(syncController.moviesDetails.get(i).getPoster());

                        moviesDetails.add(newObject);
                    }
                }

                newModel.addAttribute("moviesDetails", moviesDetails);

                addToDatabase();
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }

        return "displayInformation";
    }

    /* metoda pentru adaugarea in baza de date */
    public void addToDatabase(){

        for(int i = 0; i < moviesDetails.size(); i++){
            mainService.insertMovie(moviesDetails.get(i).getTitle(), moviesDetails.get(i).getYear(),
                moviesDetails.get(i).getUrl(), moviesDetails.get(i).getPoster(), moviesDetails.get(i).getGenre(),
                moviesDetails.get(i).getActors(), moviesDetails.get(i).getPlot(), moviesDetails.get(i).getCountry());

            System.out.println("A adaugat in baza de date");
        }
    }

    /* metoda pentru stergerea din baza de date */
    public  void deleteFromDatabase(  List<MoviesDetails> list){

        Calendar cal = Calendar.getInstance();

        for(int i = 0; i < list.size(); i++){

              /* se verifica de cat timp a fost adaugata fiecare inregistrare in baza de date */

            long difference = cal.getTimeInMillis() - list.get(i).getCurrentDate().getTime();

             /* daca diferenta dintre momentul curent si momentul in care s-a realizat salvarea in baza de date
             * este mai mare de 15 minute (900000 milisecunde), se sterge din abza de date */
            if (difference > 900000) {

                mainService.deleteMovie(list.get(i));

                System.out.println("S-a sters din baza de date");

            }
        }

    }
}