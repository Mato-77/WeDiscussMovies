package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import com.wediscussmovies.project.querymodels.MovieSuggest;
import com.wediscussmovies.project.querymodels.MovieYear;
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

     @Query(value="select new com.wediscussmovies.project.model.Movie(m.movieId, m.title, m.imdbRating, m.imageUrl, m.genres) from Movie m " +
             " join MovieGenres mg on mg.id.movieId = m.movieId "+
             " join Genre g on g.genreId = mg.id.genreId "+
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



     @Query(value = "select year,title, (num_discussions + num_replies) as discussions from ("+
             " select godini.year,m.title , count(d1.discussion_id) as num_discussions, count(r.reply_id)  as num_replies\n" +
             " from  project.movies m " +
             " left join project.discussions d1 on d1.movie_id  = m.movie_id " +
             "\t\t\t\t\t\t\t\tleft join replies r on r.discussion_id  = d1.discussion_id\n" +
             "\t\t\t\t\t\t\t\t  \t join(\n" +
             "\n" +
             " \n" +
             "\t\t\t\t\t\t\t\tselect distinct extract(year from d.date) as year\n" +
             "\t\t\t\t\t\t\t\tfrom  project.discussions d\n" +
             "\t\t\t\t\t\t\t\t) as godini on godini.year = extract(year from d1.date) or\n" +
             "\t\t\t\t\t\t\t\textract(year from r.date) = godini.year \n" +
             "\t\t\t\t\t\t\t\t\n" +
             "\t\t\t\t\t\t\t\tgroup by m.title, godini.year\n" +
             "\t\t\t\t\t\t\t\t\n" +
             "  \t\t\t\t\t\t\t) as sum_by_year\t\t\n" +
             "where (num_discussions + num_replies) = (\n" +
             " \t\n" +
             " \t\t\tselect  max(num_discussions + num_replies) from (\n" +
             " \t\t\t\t\t\t\tselect godini.year1,m.title as title1 , count(d1.discussion_id) as num_discussions, count(r.reply_id)  as                                                                                                                 \n" +
             "                                                                                                                                                   num_replies\n" +
             "\n" +
             "\t\t\t\t\t\t\t\tfrom project.movies m\n" +
             "\t\t\t\t\t\t\t\tleft join project.discussions d1 on d1.movie_id  = m.movie_id \n" +
             "\t\t\t\t\t\t\t\tleft join project.replies r on r.discussion_id  = d1.discussion_id\n" +
             "\t\t\t\t\t\t\t\t  \t join(\n" +
             "\n" +
             " \n" +
             "\t\t\t\t\t\t\t\tselect distinct extract(year from d.date) as year1\n" +
             "\t\t\t\t\t\t\t\tfrom project.discussions d\n" +
             "\t\t\t\t\t\t\t\t) as godini on godini.year1 = extract(year from d1.date) or\n" +
             "\t\t\t\t\t\t\t\textract(year from r.date) = godini.year1\n" +
             "\t\t\t\t\t\t\t\t\n" +
             "\t\t\t\t\t\t\t\tgroup by m.title, godini.year1\n" +
             "\t\t\t\t\t\t\t\t\n" +
             "  ) as diskusii_repliki " +
             " where diskusii_repliki.year1 = year " +
             "  group by diskusii_repliki.year1 " +
             " ) order by year asc, (num_discussions + num_replies) desc ",nativeQuery = true)
     List<MovieYear> findAllTopByYears();


     @Query(nativeQuery = true,value = "select title from(\n" +
             "\t select  m.title, sum(mv.stars_rated) as total\n" +
             "\t from project.replies r \n" +
             " \t join project.discussions d on r.discussion_id = d.discussion_id\n" +
             " \t join project.replies r2 on r2.discussion_id  = d.discussion_id  and r2.user_id  != ?1 \n" +
             " \t join project.users u  on u.user_id  = r2.user_id and \n" +
             " \t \n" +
             " \t \t\t(\n" +
             " \t \t\t\tselect count(r3.reply_id)\n" +
             " \t \t\t\tfrom project.replies r3 \n" +
             " \t \t\t\tgroup by u.user_id \n" +
             " \t \t\t) >= \n" +
             " \t \t\t(\n" +
             " \t \t\t\t\n" +
             " \t \t\t\t select (cast(count(*) as float )) / (select cast(count(*) as float) from project.users)\n" +
             " \t \t\t\tfrom project.replies \n" +
             " \t \t\t)\n" +
             " \t \t\t\n" +
             "     join project.movie_rates mv on mv.user_id = u.user_id \n" +
             "     join project.movies m on m.movie_id  = mv.movie_id\n" +
             "     where  r.user_id = ?1 and \n" +
             "     r.date between current_date - interval '2 months' and current_date\n" +
             "     group by m.title\n" +
             "     order by sum(mv.stars_rated) desc \n" +
             "     limit 15\n" +
             " ) as tabela")
     List<MovieSuggest> proposeMovie(Integer id);


}
