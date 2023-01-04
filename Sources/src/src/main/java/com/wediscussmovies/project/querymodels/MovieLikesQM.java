package com.wediscussmovies.project.querymodels;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import java.util.Objects;

@Data
public class MovieLikesQM {
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private Integer movieId;
    @GraphQLQuery(name = "likes",description = "Број на допаѓања")
    private Long likes;

    public MovieLikesQM(Integer movieId, Long likes) {
        this.movieId = movieId;
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieLikesQM that = (MovieLikesQM) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, likes);
    }
}
