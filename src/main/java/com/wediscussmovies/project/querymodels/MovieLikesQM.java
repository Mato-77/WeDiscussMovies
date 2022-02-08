package com.wediscussmovies.project.querymodels;

import lombok.Data;

import java.util.Objects;

@Data
public class MovieLikesQM {
    private Integer movieId;
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
