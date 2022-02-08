package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.querymodels.DiscussionLikes;
import com.wediscussmovies.project.querymodels.GenreLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
     List<Discussion> findAllByTitleLike(String title);
     List<Discussion> findAllByMovie(Movie movie);
     List<Discussion> findAllByPerson (Person person);

     @Query(value = "select new com.wediscussmovies.project.querymodels.DiscussionLikes(d.discussionId, count(dl.id.userId)) from Discussion d" +
             " left join DiscussionLikes dl on dl.id.discussionId = d.discussionId" +
             " group by d.discussionId" +
             " order by count(dl.id.userId) desc")
     @Transactional
     List<DiscussionLikes> findAllWithLikes();
}
