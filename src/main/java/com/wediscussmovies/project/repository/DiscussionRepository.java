package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
     List<Discussion> findAllByTitleLike(String title);
     List<Discussion> findAllByMovie(Movie movie);
     List<Discussion> findAllByPerson (Person person);
}
