package com.wediscussmovies.project.service;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.MovieGenres;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.MovieRates;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import com.wediscussmovies.project.querymodels.MovieSuggest;
import com.wediscussmovies.project.querymodels.MovieYear;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
     List<Movie> listAll();


     List<Movie> listAllByType(Character type);

     List<Movie> listAllWithoutDirector();

     List<Movie> searchByTitle( String title);

     Movie findById(Integer id);

     Movie findBasicById(Integer id);

     List<Integer> listAllIds();

     Movie save(String title, String description, String imageUrl,
                LocalDate airingDate, Double  rating, Integer directorId,
                List<Integer> actorIds, List<Integer> genreIds);

     Movie edit( Integer movieId, String title, String description,
                 String imageUrl, LocalDate airingDate, Double  rating,
                 Integer directorId, List<Integer> actorIds, List<Integer> genreIds);

     List<Person> findAllActorsForMovie(Movie movie);


     List<Genre> findAllGenresForMovie(Movie movie);

     void likeMovie(Integer movieId, Integer userId);

     void unlikeMovie(Integer movieId, Integer userId);

     List<Movie> findLikedMoviesByUser(User user);

     Movie deleteById(Integer id);

     void addGradeMovie(Integer movieId, User user, Grade grade);

    MovieRates addGradeMovieGraphQl(Integer movieId, Integer userId, Grade grade);


    MovieLikesQM findLikesForMovieById(int movieId);

    List<MovieActors> findAllMoviesActors();

     List<MovieGenres> findAllMovieGenres();

     List<MovieRates> findAllMovieRates();

     List<MovieLikes> findAllMovieLikes();

     List<MovieYear> findAllTopByYears();

     List<MovieSuggest> proposeMovie(Integer userId);

     List<Movie> findAll();


}
