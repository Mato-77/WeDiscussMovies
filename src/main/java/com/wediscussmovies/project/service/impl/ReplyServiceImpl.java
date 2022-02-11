package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.exception.DiscussionNotExistException;
import com.wediscussmovies.project.model.exception.ReplyNotExistException;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import com.wediscussmovies.project.model.primarykeys.UserRepliesPK;
import com.wediscussmovies.project.model.relation.UserReplies;
import com.wediscussmovies.project.repository.DiscussionRepository;
import com.wediscussmovies.project.repository.ReplyRepository;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.repository.UserRepliesRepository;
import com.wediscussmovies.project.service.ReplyService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final DiscussionRepository discussionRepository;
    private final UserRepliesRepository userRepliesRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository,
                            DiscussionRepository discussionRepository, UserRepliesRepository userRepliesRepository) {
        this.replyRepository = replyRepository;
        this.discussionRepository = discussionRepository;
        this.userRepliesRepository = userRepliesRepository;
    }



    @Override
    public void save(Integer discussionId, String text,User user) {

        Discussion discussion = this.discussionRepository.findById(discussionId).orElseThrow(() -> new DiscussionNotExistException(discussionId));

        LocalDate date = LocalDate.now();
        this.replyRepository.insertInto(text,date,user.getUserId(),discussionId);

    }

    @Override
    @GraphQLMutation(name = "likeReply")
    public void likeReply(@GraphQLArgument(name = "replyId") Integer replyId,
                   @GraphQLArgument(name = "discussionId") Integer discussionId,
                   @GraphQLArgument(name = "userId") Integer userId){
        this.userRepliesRepository.save(new UserReplies(discussionId,replyId,userId));
    }

    @Override
    @GraphQLMutation(name = "unlikeReply")
   public void unlikeReply(@GraphQLArgument(name = "replyId") Integer replyId,
                           @GraphQLArgument(name = "discussionId") Integer discussionId,
                           @GraphQLArgument(name = "userId") Integer userId) {
        // da se implementira, promena vo baza
        this.userRepliesRepository.deleteById(new UserRepliesPK(discussionId,replyId,userId));
    }

    @Override
    @GraphQLQuery(name = "discussionReplies")
  public   List<Reply> findAllByDiscussion(@GraphQLArgument(name = "discussion") Discussion discussion) {
        return this.replyRepository.findAllByDiscussion(discussion);
    }


    @Override
    @GraphQLMutation(name = "editReply")
   public Reply edit(@GraphQLArgument(name = "replyId") Integer replyId,
               @GraphQLArgument(name = "discussionId") Integer discussionId,
               @GraphQLArgument(name = "text") String text){
        ReplyPK replyPK = new ReplyPK(discussionId,replyId);
        Reply reply = this.replyRepository.findById(replyPK).orElseThrow();
        reply.setText(text);
        return  this.replyRepository.save(reply);
    }

    @Override
    @GraphQLMutation(name = "deleteReply")
    public Reply delete(@GraphQLArgument(name = "discussionId") Integer discussionId,
                 @GraphQLArgument(name = "replyId") Integer replyId) {
        Reply reply = this.findById(discussionId,replyId);
        this.replyRepository.delete(reply);
        return reply;
    }

    @Override
    @GraphQLQuery(name = "reply")
    public Reply findById(@GraphQLArgument(name = "discussionId") Integer discussionId,
                   @GraphQLArgument(name = "replyId") Integer replyId) {
        ReplyPK replyPK  = new ReplyPK(discussionId,replyId);
        return this.replyRepository.findById(replyPK).orElseThrow(() -> new ReplyNotExistException(replyPK));

    }

    @Override
    @GraphQLMutation(name = "saveReply")
    public Reply saveReply(@GraphQLArgument(name = "discussionId")Integer discussionId,
                    @GraphQLArgument(name = "text") String text, @GraphQLArgument(name = "user")User user) {
        LocalDate date = LocalDate.now();
        Discussion discussion = this.discussionRepository.findById(discussionId).orElseThrow(() -> new DiscussionNotExistException(discussionId));

        this.replyRepository.insertInto(text,date,user.getUserId(),discussionId);
        List<Reply> replies = replyRepository.findAllByDiscussion(discussion);
        return  replies.get(replies.size() -1);
    }
}
