package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.DiscussionLikes;
import com.wediscussmovies.project.querymodels.DiscussionLikesQM;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public interface DiscussionService {
    List<Discussion> listAll();

    List<Discussion> listAllByTitle(String title);

    Discussion findById( Integer id);


    void save(Character type, Integer id, String title, String text, User user);

    void edit( Integer discussionId, Character type, Integer id, String title, String text);

    void deleteById( Integer discussionId);


    void likeDiscussion(Integer discussionId, Integer userId);

    void unlikeDiscussion(Integer discussionId, Integer userId);

    List<Discussion> findAllForPersonOrMovie(Integer id,Character type);

    List<Discussion> findLikedDiscussionsByUser(User user);

    DiscussionLikesQM findLikesForDiscussionWithId(Integer discussionId);

    List<DiscussionLikesQM> findLikesForAllDiscussions();

    List<DiscussionLikes> findAllDiscussionLikes();

}
