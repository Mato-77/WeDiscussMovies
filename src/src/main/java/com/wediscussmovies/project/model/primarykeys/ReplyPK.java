package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
public class ReplyPK implements Serializable {

    @Column(name = "discussion_id")
    private int discussionId;

    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyId;

    public ReplyPK(int discussionId) {
        this.discussionId = discussionId;
    }

    public ReplyPK(int discussionId, int replyId) {
        this.discussionId = discussionId;
        this.replyId = replyId;
    }

    public ReplyPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyPK replyPK = (ReplyPK) o;
        return discussionId == replyPK.discussionId && replyId == replyPK.replyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId, replyId);
    }

    public String getString(){
        return replyId + "r \t" + discussionId + "d";
    }


}