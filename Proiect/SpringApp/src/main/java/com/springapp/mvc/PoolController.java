package com.springapp.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springapp.model.MoviesDetails;
import com.springapp.model.PoolMessageNotReady;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by astingu on 5/29/2014.
 */
public class PoolController implements Runnable {

    String server_url;
    List<MoviesDetails> moviesDetails = new ArrayList<MoviesDetails>();

    public PoolController(String server_url){
        this.server_url = server_url;
    }

    @Override
    public void run() {

        try {

             /* se parseaza json-ul luat de pe server, modific caracterele cu UTF-8, dupa care salvez doar anumite date
            * care mi s-au parut mai importante despre filmul respectiv */
            RestTemplate restTemplate = new RestTemplate();

            PoolMessageNotReady poolMessage = restTemplate.getForObject(server_url, PoolMessageNotReady.class);
            String url = poolMessage.getDetail();

            restTemplate.getForObject(url, PoolMessageNotReady.class);
            Thread.sleep(4000);

            String jsonString = restTemplate.getForObject(url, String.class);

            byte jsonBytes[] = jsonString.getBytes();

            String dataString = null;

            try {
                dataString = new String(jsonBytes, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ObjectMapper mapper = new ObjectMapper();
            List<LinkedHashMap> list = null;

            try {
                list = mapper.readValue(dataString,  List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < list.size(); i++){

                MoviesDetails newObject = new MoviesDetails();

                newObject.setTitle(list.get(i).get("Title").toString());
                newObject.setGenre(list.get(i).get("Genre").toString());
                newObject.setActors(list.get(i).get("Actors").toString());
                newObject.setPlot(list.get(i).get("Plot").toString());
                newObject.setCountry(list.get(i).get("Country").toString());

                moviesDetails.add(newObject);
            }

        } catch (InterruptedException e1) {
            e1.printStackTrace();
            System.out.println("Interrupted exception");
        } catch (NullPointerException e2){
            e2.printStackTrace();
            System.out.println("Null pointer exception");
        } catch (IndexOutOfBoundsException e3){
            e3.printStackTrace();
            System.out.println("Index out of bounds exception");
        } catch (Exception e4){
            e4.printStackTrace();
            System.out.println("Another exception");
        }
    }
}
