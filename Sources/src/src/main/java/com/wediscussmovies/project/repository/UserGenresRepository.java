package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import com.wediscussmovies.project.model.relation.UserGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGenresRepository extends JpaRepository<UserGenres, UserGenresPK> {
}
