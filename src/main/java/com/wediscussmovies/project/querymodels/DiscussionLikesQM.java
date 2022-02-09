package com.wediscussmovies.project.querymodels;

import lombok.Data;

import java.util.Objects;

@Data
public class DiscussionLikesQM {
    private Integer discussionId;
    private Long likes;

    public DiscussionLikesQM(Integer discussionId, Long likes) {
        this.discussionId = discussionId;
        this.likes = likes;
    }

    public DiscussionLikesQM() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionLikesQM that = (DiscussionLikesQM) o;
        return Objects.equals(discussionId, that.discussionId) && Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId, likes);
    }
}
