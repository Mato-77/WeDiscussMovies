package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Embeddable
public class MovieActorsPK implements Serializable {

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "actor_id")
    private int actorId;

    public MovieActorsPK(int movieId, int actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MovieActorsPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieActorsPK that = (MovieActorsPK) o;

        return actorId == that.actorId && movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + actorId;
        return result;
    }
}
