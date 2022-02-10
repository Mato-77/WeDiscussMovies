package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.querymodels.DiscussionLikesQM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
     List<Discussion> findAllByTitleLike(String title);
     List<Discussion> findAllByMovie(Movie movie);
     List<Discussion> findAllByPerson (Person person);

     @Query(value = "select new com.wediscussmovies.project.querymodels.DiscussionLikesQM(d.discussionId, count (dl.user) ) from Discussion d" +
             " left join DiscussionLikes dl on dl.id.discussionId = d.discussionId" +
             " where :id = d.discussionId" +
             " group by d.discussionId")
     @Transactional
     DiscussionLikesQM findDiscussionWithLikes(@Param("id") Integer discussionId);

     @Query(value = "select new com.wediscussmovies.project.querymodels.DiscussionLikesQM (d.discussionId, count(dl.user))  from Discussion d" +
             " left join DiscussionLikes dl on dl.id.discussionId = d.discussionId" +
             " group by d.discussionId"+
               " order by d.discussionId asc")
     @Transactional
     List<DiscussionLikesQM
             > findAllDiscussionsWithLikes();
}
