package com.wediscussmovies.project.service;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.PersonRates;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface PersonService {
    List<Person> findAll();
     List<Person> findAllDirectors();
     List<Person> findAllActors();

    Person findById(Integer person_id);
     void addGradePersonGraphQL(Integer personId, Integer userId,Grade grade);

    Person  save( String name, String surname, Character type,
                  LocalDate birthDate, String image_url, String description, List<Integer> movieIds);

    Person edit(Integer personId, String name, String surname,
                Character type, LocalDate birthDate, String image_url,
                String description, List<Integer> movieIds);


    List<Person> findPersonsByNameOrSurname( Character type, String searchQuery);


    List<Movie> findAllMoviesByPerson( Person person);


    Person deleteById( Integer id);


    void addGradePerson( Integer personId, User user, Grade grade);

    List<PersonRates> listAllPersonRates();
}
