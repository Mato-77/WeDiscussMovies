package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.querymodels.GenreLikesQM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    List<Genre> findAll();
    Genre findById(Integer id);
    List<Genre> findAllByType(String genre);
    Genre save(String genreName);
    List<GenreLikesQM> findAllWithLikes();
    void likeGenre(Integer genreId,Integer userId);
    void unlikeGenre(Integer genreId,Integer userId);

}
