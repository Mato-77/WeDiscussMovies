package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public interface ReplyService {
    @GraphQLMutation(name = "editReply")
    Reply edit(@GraphQLArgument(name = "replyId") Integer replyId,
               @GraphQLArgument(name = "discussionId") Integer discussionId,
               @GraphQLArgument(name = "text") String text);

    @GraphQLMutation(name = "deleteReply")
    Reply delete(@GraphQLArgument(name = "discussionId") Integer discussionId,
                @GraphQLArgument(name = "replyId") Integer replyId);

    @GraphQLQuery(name = "reply")
    Reply findById(@GraphQLArgument(name = "discussionId") Integer discussionId,@GraphQLArgument(name = "replyId") Integer replyId);

    @GraphQLMutation(name = "saveReply")
    Reply saveReply(@GraphQLArgument(name = "discussionId")Integer discussionId,
              @GraphQLArgument(name = "text") String text, @GraphQLArgument(name = "user")User user);

     void save(Integer discussionId, String text,User user);

    @GraphQLMutation(name = "likeReply")
    void likeReply(@GraphQLArgument(name = "replyId") Integer replyId,
                   @GraphQLArgument(name = "userId") Integer userId);

    @GraphQLMutation(name = "unlikeReply")
    void unlikeReply(@GraphQLArgument(name = "replyId") Integer replyId,
                     @GraphQLArgument(name = "userId") Integer userId);

    @GraphQLQuery(name = "discussionReplies")
    List<Reply> findAllByDiscussion(@GraphQLArgument(name = "discussion") Discussion discussion);



}
