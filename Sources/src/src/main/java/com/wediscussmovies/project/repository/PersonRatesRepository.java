package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.primarykeys.PersonRatesPK;
import com.wediscussmovies.project.model.relation.PersonRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRatesRepository extends JpaRepository<PersonRates, PersonRatesPK> {
}
