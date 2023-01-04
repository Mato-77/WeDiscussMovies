package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@Data
public class MovieGenresPK implements Serializable {

    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "genre_id")
    private int genreId;

    public MovieGenresPK(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public MovieGenresPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieGenresPK that = (MovieGenresPK) o;

        return genreId == that.genreId && movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + genreId;
        return result;
    }
}
