package com.springapp.service;


import com.springapp.model.MoviesDetails;

import java.util.List;

/**
 * Created by astingu on 5/27/2014.
 */
public interface MovieService {

    public List<MoviesDetails> find();

    public void save(MoviesDetails moviesDetails);

    public List<MoviesDetails> findMovieByName(String movieName);

    public void delete(MoviesDetails moviesDetails);
}
