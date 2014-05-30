package com.springapp.service;


import com.springapp.dao.MovieDAO;
import com.springapp.model.MoviesDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by astingu on 5/27/2014.
 */


@Service
@Transactional(readOnly = true)
public class MovieServiceImplemen implements MovieService {

    @Autowired
    private MovieDAO movieDAO;

    @Override
    @Transactional
    public void save(MoviesDetails moviesDetails){
        movieDAO.save(moviesDetails);
    }

    @Override
    public List<MoviesDetails> find() {
        return movieDAO.find();
    }

    @Override
    public List<MoviesDetails> findMovieByName(String movieName) {
        return movieDAO.findMovieByName(movieName);
    }

    @Override
    @Transactional
    public void delete(MoviesDetails moviesDetails) {
        movieDAO.delete(moviesDetails);
    }

}
