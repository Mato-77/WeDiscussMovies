package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.primarykeys.UserRepliesPK;
import com.wediscussmovies.project.model.relation.UserReplies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepliesRepository extends JpaRepository<UserReplies, UserRepliesPK> {

}
