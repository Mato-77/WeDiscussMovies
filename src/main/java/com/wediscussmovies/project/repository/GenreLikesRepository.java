package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.GenreLikesPK;
import com.wediscussmovies.project.model.primarykeys.MovieLikesPK;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.querymodels.GenreLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreLikesRepository  extends JpaRepository<UserGenres, UserGenresPK> {
    List<UserGenres> findAllByUser(User user);
}
