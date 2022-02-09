package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.DiscussionLikesPK;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.DiscussionLikes;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.UserGenres;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionLikesRepository extends JpaRepository<DiscussionLikes, DiscussionLikesPK> {
    List<DiscussionLikes> findAllByUser(User user);

}
