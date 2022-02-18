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
import com.wediscussmovies.project.querymodels.MovieSuggest;
import com.wediscussmovies.project.querymodels.MovieYear;
import com.wediscussmovies.project.repository.*;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.service.MovieService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @GraphQLQuery(name = "moviesByType")
    public List<Movie> listAllByType(@GraphQLArgument(name = "type") Character type) {
        if (type.equals('A'))
            return this.listAll();
        return this.listAllWithoutDirector();
    }

    @Override
    @GraphQLQuery(name = "moviesWithoutDirector")
    public List<Movie> listAllWithoutDirector() {
        return this.movieRepository.findAllByDirectorIsNull();
    }

    @Override
    @GraphQLQuery(name = "movie")
    public Movie findById(@GraphQLArgument(name = "id") Integer id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }

    @Override
    @GraphQLQuery(name = "movieBasic")
    public Movie findBasicById(@GraphQLArgument(name = "id") Integer id) {
        return this.movieRepository.findBasicById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }

    @Override
    @GraphQLQuery(name = "likesForMovie")
   public MovieLikesQM findLikesForMovieById(@GraphQLArgument(name = "movieId") int movieId) {
        return this.movieRepository.findLikesForMovie(movieId);
    }

    @Override
    @GraphQLQuery(name = "moviesActors")
    public List<MovieActors> findAllMoviesActors() {
        return this.movieActorsRepository.findAll();
    }

    @Override
    @GraphQLQuery(name = "moviesGenres")
    public List<MovieGenres> findAllMovieGenres() {
        return this.movieGenresRepository.findAll();
    }

    @Override
    @GraphQLQuery(name = "moviesRates")
    public List<MovieRates> findAllMovieRates() {
        return this.movieRatesRepository.findAll();
    }

    @Override
    @GraphQLQuery(name = "moviesLikes")
    public List<MovieLikes> findAllMovieLikes() {
        return this.movieLikesRepository.findAll();
    }

    @Override
    public List<MovieYear> findAllTopByYears() {
        return this.movieRepository.findAllTopByYears();
    }

    @Override
    public List<MovieSuggest> proposeMovie(Integer id) {
        User user = this.findUserByIdForGraphQl(id);
        return this.movieRepository.proposeMovie(id);
    }

    @Override
    public List<Movie> findAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public List<Integer> listAllIds() {
        return this.movieRepository.findAllMovieIds();
    }

    @Override
    @Transactional
    @GraphQLMutation(name = "saveMovie")
    public Movie save(@GraphQLArgument(name = "title") String title,
               @GraphQLArgument(name = "description") String description,
               @GraphQLArgument(name = "image") String imageUrl,
               @GraphQLArgument(name = "airingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate airingDate,
               @GraphQLArgument(name = "rating") Double  rating,
               @GraphQLArgument(name = "director") Integer directorId,
               @GraphQLArgument(name = "actorIds") List<Integer> actorIds,
               @GraphQLArgument(name = "genreIds") List<Integer> genreIds) {


        Person director =getDirector(directorId);
        Movie movie = new Movie(title,description,imageUrl,airingDate,rating,director);
       movie = this.movieRepository.saveAndFlush(movie);
       addActorsAndGenresForMovie(movie,actorIds,genreIds);
       return movie;
    }


    @Override
    @Transactional
    @GraphQLMutation(name = "editMovie")
    public Movie edit(@GraphQLArgument(name = "id") Integer movieId,
               @GraphQLArgument(name = "title") String title,
               @GraphQLArgument(name = "description") String description,
               @GraphQLArgument(name = "image") String imageUrl,
               @GraphQLArgument(name = "airingDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate airingDate,
               @GraphQLArgument(name = "rating") Double  rating,
               @GraphQLArgument(name = "director") Integer directorId,
               @GraphQLArgument(name = "actorIds") List<Integer> actorIds,
               @GraphQLArgument(name = "genreIds") List<Integer> genreIds) {


        Movie movie = this.findById(movieId);
        Person director =getDirector(directorId);

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
    @GraphQLQuery(name = "movieActors")
    public List<Person> findAllActorsForMovie(@GraphQLArgument(name = "movie") Movie movie) {
        return this.movieActorsRepository.findAllByMovie(movie)
                .stream()
                .map(MovieActors::getPerson)
                .collect(Collectors.toList());
    }

    @Override
    @GraphQLQuery(name = "movieGenres")
    public List<Genre> findAllGenresForMovie(@GraphQLArgument(name = "movie") Movie movie){
        return this.movieGenresRepository.findAllByMovie(movie)
                .stream()
                .map(MovieGenres::getGenre)
                .collect(Collectors.toList());
    }

    @Override
    @GraphQLMutation(name = "likeMovie")
    public void likeMovie(@GraphQLArgument(name = "movieId") Integer movieId,
                   @GraphQLArgument(name = "userId") Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
        Movie movie = this.findById(movieId);

        this.movieLikesRepository.save(new MovieLikes(movie, user));
    }

    @Override
    @GraphQLMutation(name = "unlikeMovie")
   public void unlikeMovie(@GraphQLArgument(name = "movieId") Integer movieId,
                     @GraphQLArgument(name = "userId") Integer userId) {
        MovieLikesPK movieLikesPK = new MovieLikesPK(movieId,userId);
        this.movieLikesRepository.deleteById(movieLikesPK);
    }

    @Override
    @GraphQLQuery(name = "likedMoviesByUser")
    public List<Movie> findLikedMoviesByUser(@GraphQLArgument(name = "user") User user) {
        return
                this.movieLikesRepository
                        .findAllByUser(user)
                        .stream()
                        .map(MovieLikes::getMovie)
                        .collect(Collectors.toList());
    }


    @Override
    @GraphQLMutation(name = "deleteMovie")
    public Movie deleteById(@GraphQLArgument(name = "id") Integer id) {
        Movie movie = this.findById(id);
        this.movieRepository.deleteById(id);
        return movie;
    }



    @Override
    public void addGradeMovie( Integer movieId,User user,Grade grade) {
         this.saveMovieRates(movieId,user,grade);
    }
    @Override
    @GraphQLMutation(name = "addGradeMovie")
    public MovieRates addGradeMovieGraphQl(@GraphQLArgument(name = "movieId") Integer movieId,
                              @GraphQLArgument(name = "userId") Integer userId,
                              @GraphQLArgument(name = "grade") Grade grade) {
        User user = this.findUserByIdForGraphQl(userId);
        return this.saveMovieRates(movieId,user,grade);
    }

    @Override
    @GraphQLQuery(name = "movieByTitle")
    public List<Movie> searchByTitle(@GraphQLArgument(name = "title") String title) {
        return this.movieRepository.findAllByTitleLike("%"+title+"%");
    }

    @Transactional
    protected void addActorsAndGenresForMovie(Movie movie, List<Integer> actorIds, List<Integer> genreIds)
    {

        if (actorIds != null) {
            actorIds.stream()
                    .map(this::findPersonById)
                    .forEach(actor -> this.movieActorsRepository.save(new MovieActors(movie, actor)));
        }
        if (genreIds != null) {
            genreIds.stream()
                    .map(this::findGenreById)
                    .forEach(genre -> this.movieGenresRepository.save(new MovieGenres(movie, genre)));
        }
    }
    private Person getDirector(Integer directorId){

        if (directorId != null)
            return this.findPersonById(directorId);
        return null;

    }
    private MovieRates saveMovieRates(Integer movieId, User user, Grade grade){
        MovieRatesPK key =  new MovieRatesPK(movieId, user.getUserId());
        Optional<MovieRates> movieRates = this.movieRatesRepository.findById(key);
        if (movieRates.isPresent()){
            MovieRates rates = movieRates.get();
            rates.setReason(grade.getReason());
            rates.setStarsRated(grade.getRating());
           return this.movieRatesRepository.save(rates);
        }
        else{
            Movie movie = this.findById(movieId);
            MovieRates rates = new MovieRates(user,movie,grade.getReason(),grade.getRating());
            return this.movieRatesRepository.save(rates);
        }
    }
    private Person findPersonById(Integer id){
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotExistException(id));
    }
    private Genre findGenreById(Integer id){
        return this.genreRepository.findById(id).orElseThrow(() -> new GenreNotExistException(id));
    }
    private User findUserByIdForGraphQl(Integer userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
    }

}


