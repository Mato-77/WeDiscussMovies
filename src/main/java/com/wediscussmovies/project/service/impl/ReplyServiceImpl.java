package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.exception.DiscussionNotExistException;
import com.wediscussmovies.project.model.exception.ReplyNotExistException;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import com.wediscussmovies.project.repository.DiscussionRepository;
import com.wediscussmovies.project.repository.ReplyRepository;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.ReplyService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final DiscussionRepository discussionRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository,
                            DiscussionRepository discussionRepository) {
        this.replyRepository = replyRepository;
        this.discussionRepository = discussionRepository;
    }



    @Override
    public void save(Integer discussionId, String text,User user) {

        Discussion discussion = this.discussionRepository.findById(discussionId).orElseThrow(() -> new DiscussionNotExistException(discussionId));

        Date date = Date.valueOf(LocalDate.now());
        this.replyRepository.insertInto(text,date,user.getUserId(),discussionId);

    }

    @Override
    public void likeReply(Integer replyId, Integer userId) {
        //da se implementira, promena vo baza
    }

    @Override
    public void unlikeReply(Integer replyId, Integer userId) {
        // da se implementira, promena vo baza
    }

    @Override
    public List<Reply> findAllByDiscussion(Discussion discussion) {
        return this.replyRepository.findAllByDiscussion(discussion);
    }


    @Override
    public Reply edit(Integer replyId,Integer discussionId,String text) {
        ReplyPK replyPK = new ReplyPK(discussionId,replyId);
        Reply reply = this.replyRepository.findById(replyPK).orElseThrow();
        reply.setText(text);
        return  this.replyRepository.save(reply);
    }

    @Override
    public Reply delete(Integer discussionId, Integer replyId) {
        Reply reply = this.findById(discussionId,replyId);
        this.replyRepository.delete(reply);
        return reply;
    }

    @Override
    public Reply findById(Integer discussionId, Integer replyId) {
        ReplyPK replyPK  = new ReplyPK(discussionId,replyId);
        return this.replyRepository.findById(replyPK).orElseThrow(() -> new ReplyNotExistException(replyPK));

    }

    @Override
    public Reply saveReply(Integer discussionId, String text, User user) {
        return null;
    }
}
