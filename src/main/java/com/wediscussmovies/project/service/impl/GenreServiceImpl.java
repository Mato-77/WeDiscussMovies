package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.exception.GenreNotExistException;
import com.wediscussmovies.project.querymodels.GenreLikes;
import com.wediscussmovies.project.repository.GenreRepository;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
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
        return this.genreRepository.findAllWithLikes();
    }

    @Override
    public List<Genre> findAll() {
        return this.genreRepository.findAll();
    }
}
