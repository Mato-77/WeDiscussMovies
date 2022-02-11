package com.wediscussmovies.project.querymodels;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionLikesQM {

    @GraphQLQuery(name = "id",description = "Идентификатор")
    private Integer discussionId;
    @GraphQLQuery(name = "likes",description = "Број на лајкови")
    private Long likes;

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
