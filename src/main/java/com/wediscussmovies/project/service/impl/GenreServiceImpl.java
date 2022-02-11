package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.GenreNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.querymodels.GenreLikesQM;
import com.wediscussmovies.project.repository.GenreLikesRepository;
import com.wediscussmovies.project.repository.GenreRepository;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.GenreService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final GenreLikesRepository genreLikesRepository;


    public GenreServiceImpl(GenreRepository genreRepository, UserRepository userRepository, GenreLikesRepository genreLikesRepository) {
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
        this.genreLikesRepository = genreLikesRepository;
    }

    @Override
    @GraphQLQuery(name = "genre")
    public Genre findById(@GraphQLArgument(name = "id") Integer id) {
        return this.genreRepository.findById(id).orElseThrow(() -> new GenreNotExistException(id));
    }

    @Override
    @GraphQLQuery(name = "genresType")
    public List<Genre> findAllByType(@GraphQLArgument(name ="genre") String genre) {
        return this.genreRepository.findAllByGenreType(genre);
    }

    @Override
    @GraphQLMutation(name = "saveGenre")
   public Genre save(@GraphQLArgument(name = "genre") String genreName) {
       Genre genre = new Genre(genreName);
        return this.genreRepository.save(genre);
    }

    @Override
    public List<GenreLikesQM> findAllWithLikes() {
        // List<GenreLikes> genreLikesList = this.genreRepository.findAllWithLikes();
       // genreLikesList.sort(GenreLikes.sorter);
        return this.genreRepository.findAllWithLikes();
    }

    @Override
    @GraphQLQuery(name = "userGenres")
   public List<Genre> findAllByUser(@GraphQLArgument(name = "user") User user) {
        return this.genreLikesRepository.findAllByUser(user);
    }

    @Override
    @GraphQLQuery(name = "genres")
    public List<Genre> findAll() {
        return this.genreRepository.findAllSorted();
    }

    @GraphQLMutation(name = "likeGenre")
   public void likeGenre(@GraphQLArgument(name = "genreId") Integer genreId,
                   @GraphQLArgument(name = "userId") Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotExistException(genreId));
        this.genreLikesRepository.save(new UserGenres(genre, user));
    }

    @Override
    @GraphQLMutation(name = "unlikeGenre")
    public void unlikeGenre(@GraphQLArgument(name = "genreId") Integer genreId,
                     @GraphQLArgument(name = "userId") Integer userId) {
        UserGenresPK movieLikesPK = new UserGenresPK(userId, genreId);
        this.genreLikesRepository.deleteById(movieLikesPK);
    }

}
