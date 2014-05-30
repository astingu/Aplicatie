package com.springapp.mainservice;

import com.springapp.model.MoviesDetails;
import com.springapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astingu on 5/27/2014.
 */


@Service
public class MainService {

    @Autowired
    private MovieService movieService;


    public void insertMovie(String title, int year, String url, String poster,
                            String genre, String actors, String plot, String country) {

        MoviesDetails moviesDetails = new MoviesDetails();

        moviesDetails.setTitle(title);
        moviesDetails.setYear(year);
        moviesDetails.setUrl(url);
        moviesDetails.setPoster(poster);
        moviesDetails.setGenre(genre);
        moviesDetails.setActors(actors);
        moviesDetails.setPlot(plot);
        moviesDetails.setCountry(country);
        movieService.save(moviesDetails);
    }

    public List<MoviesDetails> findMovieByName(String name) {

            List<MoviesDetails> listMoviesDetails = movieService.findMovieByName(name);

            return listMoviesDetails;
    }

    public List<MoviesDetails> find() {

        List<MoviesDetails> listMoviesDetails = movieService.find();

        return listMoviesDetails;
    }

    public void deleteMovie(MoviesDetails moviesDetails){

            movieService.delete(moviesDetails);

    }

}



