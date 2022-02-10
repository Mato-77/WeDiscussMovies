package com.wediscussmovies.project.service;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface MovieService {
     @GraphQLQuery(name = "movies")
     List<Movie> listAll();

     @GraphQLQuery(name = "moviesByType")
     List<Movie> listAllByType(@GraphQLArgument(name = "type") Character type);

     @GraphQLQuery(name = "moviesWithoutDirector")
     List<Movie> listAllWithoutDirector();

     @GraphQLQuery(name = "movieByTitle")
     List<Movie> searchByTitle(@GraphQLArgument(name = "title") String title);

     @GraphQLQuery(name = "movie")
     Movie findById(@GraphQLArgument(name = "id") Integer id);

     @GraphQLQuery(name = "movieBasic")
     Movie findBasicById(@GraphQLArgument(name = "id") Integer id);

     List<Integer> listAllIds();
     @GraphQLMutation(name = "saveMovie")
     Movie save(@GraphQLArgument(name = "title") String title,
                @GraphQLArgument(name = "description") String description,
                @GraphQLArgument(name = "image") String imageUrl,
                @GraphQLArgument(name = "airingDate") Date airingDate,
                @GraphQLArgument(name = "rating") Double  rating,
                @GraphQLArgument(name = "director") Integer directorId,
                @GraphQLArgument(name = "actorIds") List<Integer> actorIds,
                @GraphQLArgument(name = "genreIds") List<Integer> genreIds);

     @GraphQLMutation(name = "editMovie")
     Movie edit(@GraphQLArgument(name = "id") Integer movieId,
                @GraphQLArgument(name = "title") String title,
                @GraphQLArgument(name = "description") String description,
                @GraphQLArgument(name = "image") String imageUrl,
                @GraphQLArgument(name = "airingDate") Date airingDate,
                @GraphQLArgument(name = "rating") Double  rating,
                @GraphQLArgument(name = "director") Integer directorId,
                @GraphQLArgument(name = "actorIds") List<Integer> actorIds,
                @GraphQLArgument(name = "genreIds") List<Integer> genreIds);

     @GraphQLQuery(name = "movieActors")
     List<Person> findAllActorsForMovie(@GraphQLArgument(name = "movie") Movie movie);


     @GraphQLQuery(name = "movieGenres")
     List<Genre> findAllGenresForMovie(@GraphQLArgument(name = "movie") Movie movie);

     @GraphQLMutation(name = "likeMovie")
     void likeMovie(@GraphQLArgument(name = "movieId") Integer movieId,
                    @GraphQLArgument(name = "userId") Integer userId);

     @GraphQLMutation(name = "unlikeMovie")
     void unlikeMovie(@GraphQLArgument(name = "movieId") Integer movieId,
                      @GraphQLArgument(name = "userId") Integer userId);

     @GraphQLQuery(name = "likedMoviesByUser")
     List<Movie> findLikedMoviesByUser(@GraphQLArgument(name = "user") User user);

     void deleteById(Integer id);

     void addGradeMovie(Integer movieId, User user, Grade grade);

     @GraphQLQuery(name = "likesForMovie")
    MovieLikesQM findLikesForMovieById(@GraphQLArgument(name = "movieId") int movieId);
}
