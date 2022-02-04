package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.querymodels.GenreLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
     List<Genre> findAllByGenreType(String genre);

     @Query(value = "select new com.wediscussmovies.project.querymodels.GenreLikes(g.genreType, count(ug.id.userId)) from Genre g" +
             " left join UserGenres ug on ug.id.genreId = g.genreId" +
             " group by g.genreType" +
             " order by count(ug.id.userId) desc")
     @Transactional
     List<GenreLikes> findAllWithLikes();
}
