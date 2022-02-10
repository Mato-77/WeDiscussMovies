package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.querymodels.DiscussionLikesQM;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public interface DiscussionService {
    @GraphQLQuery(name = "discussions")
    List<Discussion> listAll();

    @GraphQLQuery(name = "filter")
    List<Discussion> listAllByTitle(@GraphQLArgument(name = "title") String title);

    @GraphQLQuery(name = "discussion")
    Discussion findById(@GraphQLArgument(name = "id") Integer id);

    @GraphQLMutation(name = "saveDiscussion")
    void save(@GraphQLArgument(name = "type") Character type,
              @GraphQLArgument(name = "id") Integer id,
              @GraphQLArgument(name = "title") String title,
              @GraphQLArgument(name = "text") String text,
              @GraphQLArgument(name = "user") User user);

    @GraphQLMutation(name = "editDiscussion")
    void edit(@GraphQLArgument(name = "id") Integer discussionId,
              @GraphQLArgument(name = "type") Character type,
              @GraphQLArgument(name = "user-id") Integer id,
              @GraphQLArgument(name = "title") String title,
              @GraphQLArgument(name = "text") String text);

    @GraphQLMutation(name = "deleteDiscussion")
    void deleteById(@GraphQLArgument(name = "id") Integer discussionId);

    @GraphQLMutation(name = "likeDiscussion")
    void likeDiscussion(@GraphQLArgument(name = "discussionId") Integer discussionId,
                        @GraphQLArgument(name="userId")Integer userId);

    @GraphQLMutation(name = "unlikeDiscussion")
    void unlikeDiscussion(@GraphQLArgument(name = "discussionId") Integer discussionId,
                          @GraphQLArgument(name="userId")Integer userId);

    List<Discussion> findAllForPersonOrMovie(Integer id,Character type);

    List<Discussion> findLikedDiscussionsByUser(User user);

    DiscussionLikesQM findLikesForDiscussionWithId(Integer discussionId);

    List<DiscussionLikesQM> findLikesForAllDiscussions();

}
