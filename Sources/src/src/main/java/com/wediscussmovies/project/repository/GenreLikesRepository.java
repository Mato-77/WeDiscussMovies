package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.UserGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreLikesRepository  extends JpaRepository<UserGenres, UserGenresPK> {


    @Query("select u.genre from UserGenres u where u.user = :user")
    List<Genre> findAllByUser(@Param("user") User user);
}
