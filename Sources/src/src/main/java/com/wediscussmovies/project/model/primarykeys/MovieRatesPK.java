package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@Data
public class MovieRatesPK implements Serializable {

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "user_id")
    private int userId;


    public MovieRatesPK() {
    }

    public MovieRatesPK(int movieId, int userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRatesPK that = (MovieRatesPK) o;



        return movieId == that.movieId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + userId;
        return result;
    }
}
