package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.MovieHasAlreadyDirector;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.model.exception.PersonNotExistException;
import com.wediscussmovies.project.model.primarykeys.PersonRatesPK;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.MovieRates;
import com.wediscussmovies.project.model.relation.PersonRates;
import com.wediscussmovies.project.repository.MovieActorsRepository;
import com.wediscussmovies.project.repository.MovieRepository;
import com.wediscussmovies.project.repository.PersonRatesRepository;
import com.wediscussmovies.project.repository.PersonRepository;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.service.PersonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MovieRepository movieRepository;
    private final MovieActorsRepository movieActorsRepository;
    private final PersonRatesRepository personRatesRepository;


    public PersonServiceImpl(PersonRepository personRepository,
                             MovieRepository movieRepository, MovieActorsRepository movieActorsRepository,
                             PersonRatesRepository personRatesRepository) {
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
        this.movieActorsRepository = movieActorsRepository;
        this.personRatesRepository = personRatesRepository;
    }

    @Override
    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    @Override
    public List<Person> findAllDirectors() {
        return this.personRepository.findAllByType('D');
    }

    @Override
    public Person findById(Integer id) {
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotExistException(id));
    }



    @Override
    public List<Person> findAllActors() {
        return this.personRepository.findAllByType('A');
    }

    @Override
    @Transactional
    public Person save(String name, String surname, Character type, Date birthDate, String image_url,
                       String description,List<Integer> movieIds) {

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
    public Person edit(Integer personId, String name, String surname,
                       Character type, Date birthDate, String image_url, String description, List<Integer> movieIds) {
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
    public List<Person> findPersonsByNameOrSurname(Character type,String searchQuery) {
        if (searchQuery != null && searchQuery.isEmpty())
            return this.personRepository.findAllByTypeAndNameLikeOrSurnameLike(type,searchQuery,searchQuery);
        if (type.equals('D'))
            return this.findAllDirectors();
        return this.findAllActors();

    }

    @Override
    public List<Movie> findAllMoviesByPerson(Person person) {
        if (person.getType().equals('A'))
            return this.movieActorsRepository
                .findAllByPerson(person)
                .stream()
                .map(MovieActors::getMovie)
                .collect(Collectors.toList());
        return this.movieRepository.findAllByDirector(person);
    }

    @Override
    public void deleteById(Integer id) {
        this.personRepository.deleteById(id);
    }

    @Override
    public void addGradePerson(Integer personId, User user, Grade grade) {
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

}
