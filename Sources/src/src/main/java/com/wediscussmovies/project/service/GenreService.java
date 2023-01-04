package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.querymodels.GenreLikesQM;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById( Integer id);

    List<Genre> findAllByType( String genre);

    Genre save( String genreName);

    List<GenreLikesQM> findAllWithLikes();

    List<Genre> findAllByUser( User user);

    void likeGenre( Integer genreId, Integer userId);

    void unlikeGenre( Integer genreId, Integer userId);

    List<UserGenres> findAllUserGenres();

}
