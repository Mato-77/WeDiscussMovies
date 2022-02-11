package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

     List<Movie> findAllByTitleLike(String Title);
     List<Movie> findAllByDirector(Person director);
     List<Movie> findAllByDirectorIsNull();
     @Query(value="select m.movieId from Movie m")
     List<Integer> findAllMovieIds();

     @Query(value="select new com.wediscussmovies.project.model.Movie(m.movieId, m.title, m.imdbRating, m.imageUrl) from Movie m" +
               " where m.movieId = :index")
     Optional<Movie> findBasicById(@Param("index")Integer index);


     @Query(value = "select new com.wediscussmovies.project.querymodels.MovieLikesQM(m.movieId, count(ml.id.userId)) from Movie m" +
             " left join MovieLikes ml on ml.id.movieId = m.movieId" +
             " group by m.movieId" +
             " order by count(ml.id.userId) desc")
     @Transactional
     List<MovieLikesQM> findAllWithLikes();

     @Query(value = "select new com.wediscussmovies.project.querymodels.MovieLikesQM(m.movieId, count(ml.id.userId)) from Movie m" +
             " left join MovieLikes ml on ml.id.movieId = m.movieId" +
             " where m.movieId = :id "+
             " group by m.movieId")
     @Transactional
     MovieLikesQM findLikesForMovie(@Param("id") Integer movie_id);


}
