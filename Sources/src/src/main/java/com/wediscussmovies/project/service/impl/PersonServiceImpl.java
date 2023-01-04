package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.MovieHasAlreadyDirector;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.model.exception.PersonNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.primarykeys.PersonRatesPK;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.PersonRates;
import com.wediscussmovies.project.repository.*;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.service.PersonService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MovieRepository movieRepository;
    private final MovieActorsRepository movieActorsRepository;
    private final PersonRatesRepository personRatesRepository;
    private final UserRepository userRepository;
    public PersonServiceImpl(PersonRepository personRepository,
                             MovieRepository movieRepository, MovieActorsRepository movieActorsRepository,
                             PersonRatesRepository personRatesRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
        this.movieActorsRepository = movieActorsRepository;
        this.personRatesRepository = personRatesRepository;
        this.userRepository = userRepository;
    }

    @Override
    @GraphQLQuery(name = "persons")
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    @Override
    @GraphQLQuery(name = "directors")
    public List<Person> findAllDirectors() {
        return this.personRepository.findAllByType('D');
    }

    @Override
    @GraphQLQuery(name = "person")
    public Person findById(@GraphQLArgument(name = "id") Integer id) {
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotExistException(id));
    }



    @Override
    @GraphQLQuery(name = "actors")
    public List<Person> findAllActors() {
        return this.personRepository.findAllByType('A');
    }

    @Override
    @Transactional
    @GraphQLMutation(name = "savePerson")
    public Person  save(@GraphQLArgument(name = "name") String name, @GraphQLArgument(name="surname") String surname,
                 @GraphQLArgument(name = "type") Character type,
                 @GraphQLArgument(name = "birthDate") LocalDate birthDate,
                 @GraphQLArgument(name = "image") String image_url,
                 @GraphQLArgument(name = "description") String description,
                 @GraphQLArgument(name = "movieIds") List<Integer> movieIds) {

        Person person = this.personRepository.saveAndFlush(new Person(name,surname,type,birthDate,image_url,description));

        if (movieIds!= null) {
            if (type.equals('A'))
                this.addActorForMovies(person, movieIds);
            else
                this.addDirectorForMovies(person, movieIds);
        }
         return person;

    }

    @Override
    @Transactional
    @GraphQLMutation(name = "editPerson")
   public Person edit(@GraphQLArgument(name = "id") Integer personId,
                @GraphQLArgument(name = "name") String name,
                @GraphQLArgument(name = "surname") String surname,
                @GraphQLArgument(name = "type") Character type,
                @GraphQLArgument(name = "birthDate") LocalDate birthDate,
                @GraphQLArgument(name = "image") String image_url,
                @GraphQLArgument(name = "description") String description,
                @GraphQLArgument(name = "movieIds") List<Integer> movieIds) {
        Person person = this.findById(personId);

        person.setName(name);
        person.setSurname(surname);
        person.setDateOfBirth(birthDate);
        person.setImageUrl(image_url);
        person.setType(type);
        person.setDescription(description);


        person = this.personRepository.saveAndFlush(person);

            this.movieActorsRepository.deleteAllByPerson(person);
            this.removeMovieDirectorsBeforeEdit(person);
        if (movieIds != null) {
            if (type.equals('A'))
                this.addActorForMovies(person, movieIds);
            else
                this.addDirectorForMovies(person, movieIds);
        }

        return person;

    }

    @Override
    @GraphQLQuery(name = "personsByNameOrSurname")
    public List<Person> findPersonsByNameOrSurname(@GraphQLArgument(name = "type") Character type,
                                            @GraphQLArgument(name = "name") String searchQuery) {
        if (searchQuery != null && searchQuery.isEmpty())
            return this.personRepository.findAllByTypeAndNameLikeOrSurnameLike(type,searchQuery,searchQuery);
        if (type.equals('D'))
            return this.findAllDirectors();
        return this.findAllActors();

    }

    @Override
    @GraphQLQuery(name = "moviesPerson")
    public List<Movie> findAllMoviesByPerson(@GraphQLArgument(name = "person") Person person){
        if (person.getType().equals('A'))
            return this.movieActorsRepository
                .findAllByPerson(person)
                .stream()
                .map(MovieActors::getMovie)
                .collect(Collectors.toList());
        return this.movieRepository.findAllByDirector(person);
    }

    @Override
    @GraphQLMutation(name = "deletePerson")
    public  Person deleteById(@GraphQLArgument(name = "id") Integer id) {
        Person person = this.findById(id);
        this.personRepository.delete(person);
        return person;
    }

    @Override
    public void addGradePerson( Integer personId,
                         User user,
                        Grade grade) {

        addGrade(personId,user,grade);

    }
    @Override
    @GraphQLMutation(name = "addGradePerson")
    public void addGradePersonGraphQL(@GraphQLArgument(name = "id") Integer personId,
                               @GraphQLArgument(name = "userId") Integer userId,
                               @GraphQLArgument(name = "grade") Grade grade) {
        User user = this.findUserByIdForGraphQl(userId);

        addGrade(personId,user,grade);


    }
    @Override
    @GraphQLMutation(name="personRates")
    public List<PersonRates> listAllPersonRates(){
        return this.personRatesRepository.findAll();
    }


    private void addGrade(Integer personId, User user, Grade grade){
        PersonRatesPK key =  new PersonRatesPK(personId, user.getUserId());
        Optional<PersonRates> personRates = this.personRatesRepository.findById(key);
        if (personRates.isPresent()){
            PersonRates rates = personRates.get();
            rates.setReason(grade.getReason());
            rates.setStarsRated(grade.getRating());
            this.personRatesRepository.save(rates);
        }
        else{
            Person person = this.findById(personId);
            PersonRates rates = new PersonRates(user,person,grade.getReason(),grade.getRating());
            this.personRatesRepository.save(rates);
        }
    }
    private void addActorForMovies(Person person, List<Integer> movieIds){

        movieIds.stream()
                .map(this::findMovieById)
                .forEach(m -> this.movieActorsRepository.save(new MovieActors(m,person)));

    }
    private void addDirectorForMovies(Person person, List<Integer> movieIds){
        movieIds.stream()
                .map(this::findMovieById)
                .forEach(m -> this.addMovieDirector(m,person));
    }
    private void addMovieDirector(Movie movie,Person person){
        Person director = movie.getDirector();
        if (director != null)
            throw new MovieHasAlreadyDirector(movie.getTitle(),director.getName(),director.getSurname());
        movie.setDirector(person);
        this.movieRepository.save(movie);
    }
    private void removeMovieDirectorsBeforeEdit(Person person){
        this.movieRepository.findAllByDirector(person)
                .forEach(m ->{ m.setDirector(null); this.movieRepository.save(m); });

    }

    private Movie findMovieById(Integer id){
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }
    private User findUserByIdForGraphQl(Integer userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
    }

}
