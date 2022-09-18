package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.relation.MovieGenres;
import com.wediscussmovies.project.model.primarykeys.MovieGenresPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenresRepository extends JpaRepository<MovieGenres, MovieGenresPK> {
    List<MovieGenres> deleteAllByMovie(Movie movie);
    List<MovieGenres> findAllByMovie(Movie movie);
}
