package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
     List<Person> findAllByType(Character type);
     List<Person> findAllByTypeAndNameLike(Character type, String name);
     List<Person> findAllByTypeAndSurnameLike(Character type, String surname);
    // Optional<Person> findPersonByPersonIdAndType(Integer id, Character type);
     List<Person> findAllByTypeAndNameLikeOrSurnameLike(Character type,String name, String surname);
}
