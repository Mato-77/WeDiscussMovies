package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;

import java.util.List;

public interface ReplyService {
    Reply edit(Integer replyId, Integer discussionId, String text);
    void delete(Integer discussionId,Integer replyId);
    Reply findById(Integer discussionId, Integer replyId);
    void save(Integer discussionId, String text, User user);
    void likeReply(Integer replyId,Integer userId);
    void unlikeReply(Integer replyId,Integer userId);
    List<Reply> findAllByDiscussion(Discussion discussion);



}
