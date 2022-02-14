package com.wediscussmovies.project.model.primarykeys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserRepliesPK implements Serializable {

    @Column(name = "discussion_id")
    private Integer discussionId;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "user_id")
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRepliesPK that = (UserRepliesPK) o;
        return Objects.equals(discussionId, that.discussionId) && Objects.equals(replyId, that.replyId) && Objects.equals(userId, that.userId);
    }

    public ReplyPK getKey(){
        return new ReplyPK(discussionId, replyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId, replyId, userId);
    }
}
