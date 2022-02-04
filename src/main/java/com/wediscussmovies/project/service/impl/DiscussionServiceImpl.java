package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.exception.DiscussionNotExistException;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.model.exception.PersonNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.repository.DiscussionRepository;
import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.repository.MovieRepository;
import com.wediscussmovies.project.repository.PersonRepository;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.DiscussionService;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
import com.wediscussmovies.project.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;

    public DiscussionServiceImpl(DiscussionRepository discussionRepository, UserRepository userRepository,
                                 MovieRepository movieRepository, PersonRepository personRepository) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
    }




    @Override
    public List<Discussion> listAll() {
        return this.discussionRepository.findAll();
    }

    @Override
    public void save(Character type,Integer id,String title, String text,User user) {
        Date date = Date.valueOf(LocalDate.now());
        Discussion discussion;

        if (type.equals('M')) {
            discussion = new Discussion('M',text,title,date,user);
            this.addMovieForDiscussion(id,discussion);
        }
        else{
            discussion = new Discussion('P',text,title,date,user);
            this.addPersonForDiscussion(id,discussion);
        }



        discussionRepository.save(discussion);
    }

    @Override
    public void edit(Integer discussionId, Character type, Integer id, String title, String text) {
        Discussion discussion = this.findById(discussionId);

        discussion.setText(text);
        discussion.setTitle(title);
        discussion.setType(type);
        discussion.setMovie(null);
        discussion.setPerson(null);

        if (type.equals('M')) {
            this.addMovieForDiscussion(id,discussion);
        }
        else{
          this.addPersonForDiscussion(id,discussion);
        }
        this.discussionRepository.save(discussion);

    }

    @Override
    public void deleteById(Integer discussionId) {
        this.discussionRepository.deleteById(discussionId);
    }

    @Override
    public void likeDiscussion(Integer discussionId, Integer userId) {
        //ova fali od dijagram
    }

    @Override
    public void unlikeDiscussion(Integer discussionId, Integer userId) {
        // ova fali od dijagram
    }

    @Override
    public List<Discussion> findAllForPersonOrMovie(Integer id,Character type) {
        if (type.equals('M'))
        {
            Movie movie = this.findMovieById(id);
            return this.discussionRepository.findAllByMovie(movie);
        }
        Person person  = this.findPersonById(id);
        return this.discussionRepository.findAllByPerson(person);

    }

    @Override
    public Discussion findById(Integer id) {
        return discussionRepository.findById(id).orElseThrow(() -> new DiscussionNotExistException(id));
    }

    @Override
    public List<Discussion> listAllByTitle(String title) {
        if(title == null || title.isEmpty())
         return this.listAll();
        return discussionRepository.findAllByTitleLike("%"+title+"%");
    }
    private void addMovieForDiscussion(Integer id,Discussion discussion){
        Movie movie = findMovieById(id);
        discussion.setMovie(movie);

    }

    private void addPersonForDiscussion(Integer id, Discussion discussion){
        Person person = findPersonById(id);
        discussion.setPerson(person);

    }
    private Movie findMovieById(Integer id){
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieIdNotFoundException(id));
    }
    private Person findPersonById(Integer id){
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotExistException(id));

    }
}
