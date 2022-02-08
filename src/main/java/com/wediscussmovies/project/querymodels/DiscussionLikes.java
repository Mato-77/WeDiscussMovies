package com.wediscussmovies.project.querymodels;

import lombok.Data;

import java.util.Objects;

@Data
public class DiscussionLikes {
    private Integer discussionId;
    private Long likes;

    public DiscussionLikes(Integer discussionId, Long likes) {
        this.discussionId = discussionId;
        this.likes = likes;
    }

    public DiscussionLikes() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionLikes that = (DiscussionLikes) o;
        return Objects.equals(discussionId, that.discussionId) && Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId, likes);
    }
}
