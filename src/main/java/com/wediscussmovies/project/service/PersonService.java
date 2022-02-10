package com.wediscussmovies.project.service;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.MovieLikes;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface PersonService {
    @GraphQLQuery(name = "persons")
    List<Person> findAll();
    @GraphQLQuery(name = "directors")
     List<Person> findAllDirectors();
    @GraphQLQuery(name = "actors")
     List<Person> findAllActors();

    @GraphQLQuery(name = "person")
    Person findById(@GraphQLArgument(name = "id") Integer person_id);

    @GraphQLMutation(name = "savePerson")
    Person  save(@GraphQLArgument(name = "name") String name, @GraphQLArgument(name="surname") String surname,
                 @GraphQLArgument(name = "type") Character type,
                 @GraphQLArgument(name = "birthDate")  Date birthDate,
                 @GraphQLArgument(name = "image") String image_url,
                 @GraphQLArgument(name = "description") String description,
                 @GraphQLArgument(name = "movieIds") List<Integer> movieIds);

    @GraphQLMutation(name = "editPerson")
    Person edit(@GraphQLArgument(name = "id") Integer personId,
                @GraphQLArgument(name = "name") String name,
                @GraphQLArgument(name = "surname") String surname,
                @GraphQLArgument(name = "type") Character type,
                @GraphQLArgument(name = "birthDate") Date birthDate,
                @GraphQLArgument(name = "image") String image_url,
                @GraphQLArgument(name = "description") String description,
                @GraphQLArgument(name = "movieIds") List<Integer> movieIds);

    @GraphQLQuery(name = "personsByNameOrSurname")
    List<Person> findPersonsByNameOrSurname(@GraphQLArgument(name = "type") Character type,@GraphQLArgument(name = "name") String searchQuery);

    @GraphQLQuery(name = "moviesPerson")
    List<Movie> findAllMoviesByPerson(@GraphQLArgument(name = "person") Person person);

    @GraphQLMutation(name = "deletePerson")
    void deleteById(@GraphQLArgument(name = "id") Integer id);

    @GraphQLMutation(name = "gradePerson")
    void addGradePerson(@GraphQLArgument(name = "id") Integer personId,
                        @GraphQLArgument(name = "user") User user,
                        @GraphQLArgument(name = "grade") Grade grade);
}
