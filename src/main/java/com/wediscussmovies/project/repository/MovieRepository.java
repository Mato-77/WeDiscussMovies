package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
     List<Movie> findAllByTitleLike(String Title);
     List<Movie> findAllByDirector(Person director);
     List<Movie> findAllByDirectorIsNull();
     @Query(value="select m.movieId from Movie m")
     List<Integer> findAllMovieIds();
}
