package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.querymodels.GenreLikesQM;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    @GraphQLQuery(name = "genres")
    List<Genre> findAll();

    @GraphQLQuery(name = "genre")
    Genre findById(@GraphQLArgument(name = "id") Integer id);

    @GraphQLQuery(name = "genresType")
    List<Genre> findAllByType(@GraphQLArgument(name ="genre") String genre);

    @GraphQLMutation(name = "saveGenre")
    Genre save(@GraphQLArgument(name = "genre") String genreName);

    List<GenreLikesQM> findAllWithLikes();

    @GraphQLQuery(name = "userGenres")
    List<Genre> findAllByUser(@GraphQLArgument(name = "user") User user);

    @GraphQLMutation(name = "likeGenre")
    void likeGenre(@GraphQLArgument(name = "genreId") Integer genreId,
                   @GraphQLArgument(name = "userId") Integer userId);

    @GraphQLMutation(name = "unlikeGenre")
    void unlikeGenre(@GraphQLArgument(name = "genreId") Integer genreId,
                     @GraphQLArgument(name = "userId") Integer userId);

}
