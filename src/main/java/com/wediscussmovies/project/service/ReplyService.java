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
    Reply edit(Integer replyId, Integer discussionId, String text);

    Reply delete(Integer discussionId, Integer replyId);

    Reply findById(Integer discussionId, Integer replyId);

    Reply saveReply(Integer discussionId, String text,User user);

     void save(Integer discussionId, String text,User user);

    void likeReply( Integer replyId,Integer discussionId, Integer userId);

    void unlikeReply(Integer replyId,Integer discussionId, Integer userId);

    List<Reply> findAllByDiscussion (Discussion discussion);

    List<ReplyPK> findAllLikedByUser(User user);
}
