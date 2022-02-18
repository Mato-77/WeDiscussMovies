package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.primarykeys.MovieRatesPK;
import com.wediscussmovies.project.model.relation.MovieRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRatesRepository extends JpaRepository<MovieRates, MovieRatesPK> {
}
