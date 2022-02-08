package com.wediscussmovies.project.service;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface MovieService {
     List<Movie> listAll();
     List<Movie> listAllByType(Character type);
     List<Movie> listAllWithoutDirector();
     List<Movie> searchByTitle(String title);
     Movie findById(Integer id);
     Movie findBasicById(Integer id);
     List<Integer> listAllIds();
     Movie save(String title, String description, String imageUrl, Date airingDate,Double  rating,
                Integer directorId,List<Integer> actorIds,List<Integer> genreIds);

     Movie edit(Integer movieId,String title, String description, String imageUrl, Date airingDate,Double  rating,
                Integer directorId,List<Integer> actorIds, List<Integer> genreIds);

     List<Person> findAllActorsForMovie(Movie movie);
     List<Genre> findAllGenresForMovie(Movie movie);

     void likeMovie(Integer movieId, Integer userId);
     void unlikeMovie(Integer movieId,Integer userId);

     List<Movie> findLikedMoviesByUser(User user);

     void deleteById(Integer id);

     void addGradeMovie(Integer movieId, User user, Grade grade);

    MovieLikesQM findLikesForMovieById(int movieId);
}
