package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.UserGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreLikesRepository  extends JpaRepository<UserGenres, UserGenresPK> {
    List<UserGenres> findAllByUser(User user);
}
