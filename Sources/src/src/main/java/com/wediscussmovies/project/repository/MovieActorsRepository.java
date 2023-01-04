package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.primarykeys.MovieActorsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorsRepository extends JpaRepository<MovieActors, MovieActorsPK> {

    List<MovieActors> deleteAllByMovie(Movie movie);
    List<MovieActors> findAllByMovie(Movie movie);
    List<MovieActors> findAllByPerson(Person person);
    void deleteAllByPerson(Person person);
}
