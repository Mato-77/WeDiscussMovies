package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.UserRepliesPK;
import com.wediscussmovies.project.model.relation.UserReplies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepliesRepository extends JpaRepository<UserReplies, UserRepliesPK> {
    List<UserReplies> findAllByUser(User user);
}
