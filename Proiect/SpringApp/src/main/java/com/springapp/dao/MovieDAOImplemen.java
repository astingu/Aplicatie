package com.springapp.dao;


import com.springapp.model.MoviesDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by astingu on 5/27/2014.
 */


@Repository
public class MovieDAOImplemen implements MovieDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private String QUERY_FIND_MOVIE_BY_NAME = "Select m from MoviesDetails m where m.title like :movie_name";
    private String QUERY = "Select m from MoviesDetails m";

    @Override
    public void save(MoviesDetails moviesDetails){
        entityManager.persist(moviesDetails);
        entityManager.flush();
    }

    public List<MoviesDetails> find() {
        return entityManager.createQuery(QUERY).getResultList();
    }

    public List<MoviesDetails> findMovieByName(String movieName) {
        String auxiliar = "%" + movieName + "%";

        return entityManager.createQuery(QUERY_FIND_MOVIE_BY_NAME)
                .setParameter("movie_name", auxiliar).getResultList();
    }

    @Override
    public void delete(MoviesDetails moviesDetails) {
        entityManager.remove(entityManager.merge(moviesDetails));
        entityManager.flush();
    }


}
