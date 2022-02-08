package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.querymodels.GenreLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
     List<Genre> findAllByGenreType(String genre);

     @Query(value = "select new com.wediscussmovies.project.querymodels.GenreLikes(g.genreId, g.genreType, count(ug.id.userId)) from Genre g" +
             " left join UserGenres ug on ug.id.genreId = g.genreId" +
             " group by g.genreId, g.genreType" +
             " order by count(ug.id.userId) desc")
     @Transactional
     List<GenreLikes> findAllWithLikes();

     @Modifying
     @Query(value = "insert into project.user_genres (user_id,genre_id) values(:user_id,:genre_id)",nativeQuery = true)
     @Transactional
     void insertInto(@Param("user_id")Integer userId, @Param("genre_id")Integer genreId);

     @Query(value = "select new com.wediscussmovies.project.model.Genre (g.genreId, g.genreType) from Genre g" +
     " ORDER BY g.genreType DESC")
     List<Genre> findAllSorted();
}
