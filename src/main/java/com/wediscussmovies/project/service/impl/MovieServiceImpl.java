package com.wediscussmovies.project.service.impl;


import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.*;
import com.wediscussmovies.project.model.exception.GenreNotExistException;
import com.wediscussmovies.project.model.exception.PersonNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.primarykeys.MovieLikesPK;
import com.wediscussmovies.project.model.primarykeys.MovieRatesPK;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.MovieGenres;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.MovieRates;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import com.wediscussmovies.project.repository.*;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.service.MovieService;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;
    private final MovieActorsRepository movieActorsRepository;
    private final MovieGenresRepository movieGenresRepository;
    private final UserRepository userRepository;
    private final MovieLikesRepository movieLikesRepository;
    private final MovieRatesRepository movieRatesRepository;

    public MovieServiceImpl(MovieRepository movieRepository, PersonRepository personRepository,
                            GenreRepository genreRepository, MovieActorsRepository movieActorsRepository,
                            MovieGenresRepository movieGenresRepository,
                            UserRepository userRepository, MovieLikesRepository movieLikesRepository,
                            MovieRatesRepository movieRatesRepository) {

        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.genreRepository = genreRepository;
        this.movieActorsRepository = movieActorsRepository;
        this.movieGenresRepository = movieGenresRepository;
        this.userRepository = userRepository;
        this.movieLikesRepository = movieLikesRepository;
        this.movieRatesRepository = movieRatesRepository;
    }

    @Override
    @GraphQLQuery(name = "movies")
    public List<Movie> listAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public List<Movie> listAllByType(Character type) {
        if (type.equals('A'))
            return this.listAll();
        return this.listAllWithoutDirector();
    }

    @Override
    public List<Movie> listAllWithoutDirector() {
        return this.movieRepository.findAllByDirectorIsNull();
    }

    @Override
    public Movie findById(Integer id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }

    @Override
    public Movie findBasicById(Integer id) {
        return this.movieRepository.findBasicById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }

    @Override
    public MovieLikesQM findLikesForMovieById(int movieId) {
        return this.movieRepository.findLikesForMovie(movieId).get(0);
    }

    @Override
    public List<Integer> listAllIds() {
        return this.movieRepository.findAllMovieIds();
    }

    @Override
    @Transactional
    public Movie save(String title, String description, String imageUrl,
                      Date airingDate, Double rating, Integer directorId,List<Integer> actorIds,List<Integer> genreIds) {


        Person director =getDirector(directorId);
        Movie movie = new Movie(title,description,imageUrl,airingDate,rating,director);
       movie = this.movieRepository.saveAndFlush(movie);
       addActorsAndGenresForMovie(movie,actorIds,genreIds);
       return movie;
    }


    @Transactional
    @Override
    public Movie edit(Integer movieId, String title, String description,
                      String imageUrl, Date airingDate, Double rating,
                      Integer directorId, List<Integer> actorIds,List<Integer> genreIds) {


        Person director =getDirector(directorId);
        Movie movie = this.findById(movieId);

        movie.setTitle(title);
        movie.setDescription(description);
        movie.setImageUrl(imageUrl);
        movie.setAiringDate(airingDate);
        movie.setImdbRating(rating);
        movie.setDirector(director);

        this.movieActorsRepository.deleteAllByMovie(movie);
        this.movieGenresRepository.deleteAllByMovie(movie);

        movie = this.movieRepository.save(movie);
        this.addActorsAndGenresForMovie(movie,actorIds,genreIds);

        return movie;
    }

    @Override
    public List<Person> findAllActorsForMovie(Movie movie) {
        return this.movieActorsRepository.findAllByMovie(movie)
                .stream()
                .map(MovieActors::getPerson)
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> findAllGenresForMovie(Movie movie) {
        return this.movieGenresRepository.findAllByMovie(movie)
                .stream()
                .map(MovieGenres::getGenre)
                .collect(Collectors.toList());
    }

    @Override
    public void likeMovie(Integer movieId, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
        Movie movie = this.findById(movieId);

        this.movieLikesRepository.save(new MovieLikes(movie, user));
    }

    @Override
    public void unlikeMovie(Integer movieId, Integer userId) {
        MovieLikesPK movieLikesPK = new MovieLikesPK(movieId,userId);
        this.movieLikesRepository.deleteById(movieLikesPK);
    }

    @Override
    public List<Movie> findLikedMoviesByUser(User user) {
        return
                this.movieLikesRepository
                        .findAllByUser(user)
                        .stream()
                        .map(MovieLikes::getMovie)
                        .collect(Collectors.toList());
    }


    @Override
    public void deleteById(Integer id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public void addGradeMovie(Integer movieId, User user, Grade grade) {
        MovieRatesPK key =  new MovieRatesPK(movieId, user.getUserId());
        Optional<MovieRates> movieRates = this.movieRatesRepository.findById(key);
        if (movieRates.isPresent()){
           MovieRates rates = movieRates.get();
            rates.setReason(grade.getReason());
            rates.setStarsRated(grade.getRating());
            this.movieRatesRepository.save(rates);
        }
        else{
            Movie movie = this.findById(movieId);
            MovieRates rates = new MovieRates(user,movie,grade.getReason(),grade.getRating());
            this.movieRatesRepository.save(rates);
        }
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return this.movieRepository.findAllByTitleLike("%"+title+"%");
    }

    @Transactional
    void addActorsAndGenresForMovie(Movie movie, List<Integer> actorIds, List<Integer> genreIds)
    {

        actorIds.stream()
                .map(this::findPersonById)
                .forEach(actor -> this.movieActorsRepository.save(new MovieActors(movie,actor)));

        genreIds.stream()
                .map(this::findGenreById)
                .forEach(genre -> this.movieGenresRepository.save(new MovieGenres(movie,genre)));

    }
    private Person getDirector(Integer directorId){

        if (directorId != null)
            return this.findPersonById(directorId);
        return null;

    }
    private Person findPersonById(Integer id){
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotExistException(id));
    }
    private Genre findGenreById(Integer id){
        return this.genreRepository.findById(id).orElseThrow(() -> new GenreNotExistException(id));
    }
}


