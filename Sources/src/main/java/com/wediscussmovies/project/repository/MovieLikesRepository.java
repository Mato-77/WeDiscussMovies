package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.MovieLikesPK;
import com.wediscussmovies.project.model.relation.MovieLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieLikesRepository extends JpaRepository<MovieLikes, MovieLikesPK> {
    List<MovieLikes> findAllByUser(User user);
}
