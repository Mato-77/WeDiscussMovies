package com.wediscussmovies.project.model.primarykeys;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class DiscussionLikesPK implements Serializable {
    @Column(name = "discussion_id")
    private int discussionId;

    @Column(name = "user_id")
    private int userId;

    public DiscussionLikesPK(int discussionId, int userId) {
        this.discussionId = discussionId;
        this.userId = userId;
    }

    public DiscussionLikesPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionLikesPK that = (DiscussionLikesPK) o;
        return discussionId == that.discussionId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId, userId);
    }
}
