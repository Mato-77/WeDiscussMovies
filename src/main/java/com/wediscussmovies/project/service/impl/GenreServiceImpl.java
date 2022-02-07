package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.GenreNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.primarykeys.GenreLikesPK;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.querymodels.GenreLikes;
import com.wediscussmovies.project.repository.GenreLikesRepository;
import com.wediscussmovies.project.repository.GenreRepository;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.GenreService;
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
    public Genre findById(Integer id) {
        return this.genreRepository.findById(id).orElseThrow(() -> new GenreNotExistException(id));
    }

    @Override
    public List<Genre> findAllByType(String genre) {
        return this.genreRepository.findAllByGenreType(genre);
    }

    @Override
    public Genre save(String genreName) {
       Genre genre = new Genre(genreName);
        return this.genreRepository.save(genre);
    }

    @Override
    public List<GenreLikes> findAllWithLikes() {
        List<GenreLikes> genreLikesList = this.genreRepository.findAllWithLikes();
        genreLikesList.sort(GenreLikes.sorter);
        return genreLikesList;
    }

    @Override
    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }

    @Override
    public void likeGenre(Integer genreId, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new GenreNotExistException(genreId));
        this.genreLikesRepository.save(new UserGenres(genre, user));
    }

    @Override
    public void unlikeGenre(Integer genreId, Integer userId) {
        UserGenresPK movieLikesPK = new UserGenresPK(userId, genreId);
        this.genreLikesRepository.deleteById(movieLikesPK);
    }
}
