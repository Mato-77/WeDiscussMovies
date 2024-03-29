package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.exception.DiscussionNotExistException;
import com.wediscussmovies.project.model.exception.MovieIdNotFoundException;
import com.wediscussmovies.project.model.exception.PersonNotExistException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.primarykeys.DiscussionLikesPK;
import com.wediscussmovies.project.model.relation.DiscussionLikes;
import com.wediscussmovies.project.querymodels.DiscussionLikesQM;
import com.wediscussmovies.project.repository.*;
import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.service.DiscussionService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionLikesRepository discussionLikesRepository;
    private final UserRepository userRepository;

    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;

    public DiscussionServiceImpl(DiscussionRepository discussionRepository, DiscussionLikesRepository discussionLikesRepository, UserRepository userRepository,
                                 MovieRepository movieRepository, PersonRepository personRepository) {
        this.discussionRepository = discussionRepository;
        this.discussionLikesRepository = discussionLikesRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
    }




    @Override
    @GraphQLQuery(name = "discussions")
    public List<Discussion> listAll() {
        List<Discussion> discussions = this.discussionRepository.findAll();
        List<DiscussionLikesQM> discussionLikes  = this.discussionRepository.findAllDiscussionsWithLikes();
        for (int i = 0; i < discussions.size(); i++){
            boolean likesSet = false;
            for(int j=0; j < discussionLikes.size(); j++){
                Discussion d = discussions.get(i);
                DiscussionLikesQM dl = discussionLikes.get(j);
                Integer d2 = dl.getDiscussionId();
                if(d.getDiscussionId() == d2){
                    d.setLikes(dl.getLikes());
                    likesSet = true;
                    break;
                }
            }
            if(!likesSet){
                discussions.get(i).setLikes(0);
            }
        }
        return discussions;
    }

    @Override
    @GraphQLMutation(name = "saveDiscussion")
   public void save(@GraphQLArgument(name = "type") Character type,
              @GraphQLArgument(name = "id") Integer id,
              @GraphQLArgument(name = "title") String title,
              @GraphQLArgument(name = "text") String text,
              @GraphQLArgument(name = "user") User user) {
        LocalDate date = LocalDate.now();
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
    @GraphQLMutation(name = "editDiscussion")
    public void edit(@GraphQLArgument(name = "id") Integer discussionId,
              @GraphQLArgument(name = "type") Character type,
              @GraphQLArgument(name = "userId") Integer id,
              @GraphQLArgument(name = "title") String title,
              @GraphQLArgument(name = "text") String text) {
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
    @GraphQLMutation(name = "deleteDiscussion")
    public void deleteById(@GraphQLArgument(name = "id") Integer discussionId) {
        this.discussionRepository.deleteById(discussionId);
    }

    @Override
    @GraphQLMutation(name = "likeDiscussion")
    public void likeDiscussion(@GraphQLArgument(name = "discussionId") Integer discussionId,
                               @GraphQLArgument(name="userId")Integer userId) {
        Discussion discussion = discussionRepository.findById(discussionId).orElseThrow(() -> new DiscussionNotExistException(discussionId));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotExistException(userId.toString()));
        this.discussionLikesRepository.save(new DiscussionLikes(discussion, user));
    }

    @Override
    @GraphQLQuery(name = "likedDiscussionsByUser")
    public List<Discussion> findLikedDiscussionsByUser(@GraphQLArgument(name = "user") User user) {
        List<DiscussionLikes> likes = discussionLikesRepository.findAllByUser(user);
        List<Discussion> discussions = new ArrayList<>();
        for(DiscussionLikes dl: likes){
            discussions.add(dl.getDiscussion());
        }
        return discussions;
    }

    @Override
    @GraphQLMutation(name = "unlikeDiscussion")
    public void unlikeDiscussion(@GraphQLArgument(name = "discussionId") Integer discussionId,
    @GraphQLArgument(name="userId")Integer userId) {
        DiscussionLikesPK pk = new DiscussionLikesPK(discussionId, userId);
        this.discussionLikesRepository.deleteById(pk);
    }

    @Override
    public DiscussionLikesQM findLikesForDiscussionWithId(Integer discussionId) {
//        return discussionRepository.findAllWithLikes().stream().filter(d ->  d.getDiscussionId().equals(discussionId)).findFirst().get();
                return this.discussionRepository.findDiscussionWithLikes(discussionId);

    }

    @Override
    public List<DiscussionLikesQM> findLikesForAllDiscussions() {
           // return this.discussionRepository.findAllDiscussionsWithLikes();
        return this.discussionRepository.findAllDiscussionsWithLikes();
    }

    @Override
    @GraphQLQuery(name = "discussionsLikes")
    public List<DiscussionLikes> findAllDiscussionLikes() {
        return this.discussionLikesRepository.findAll();
    }


    @Override
    public List<Discussion> findAllForPersonOrMovie(Integer id,Character type) {
        List<Discussion> list;
        if (type.equals('M'))
        {
            Movie movie = this.findMovieById(id);
            list = this.discussionRepository.findAllByMovie(movie);
        }
        else {
            Person person = this.findPersonById(id);
            list = this.discussionRepository.findAllByPerson(person);
        }
        List<DiscussionLikesQM> discussionLikes = findLikesForAllDiscussions();
        for (int i = 0; i < list.size(); i++) {
            boolean likesSet = false;
            for (int j = 0; j < discussionLikes.size(); j++) {
                Discussion d = list.get(i);
                DiscussionLikesQM dl = discussionLikes.get(j);
                Integer d2 = dl.getDiscussionId();
                if (d.getDiscussionId() == d2) {
                    d.setLikes(dl.getLikes());
                    likesSet = true;
                    break;
                }
            }
            if (!likesSet) {
                list.get(i).setLikes(0);
            }
        }
        return list;
    }

    @Override
    public Discussion findById(Integer id) {
        Discussion disc =  discussionRepository.findById(id).orElseThrow(() -> new DiscussionNotExistException(id));
        if (this.discussionRepository.findDiscussionWithLikes(id) != null)
        disc.setLikes(this.discussionRepository.findDiscussionWithLikes(id).getLikes());
        return disc;
    }

    @Override
    @GraphQLQuery(name = "filter")
    public List<Discussion> listAllByTitle(@GraphQLArgument(name = "title") String title) {
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
