package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GenreLikesPK implements Serializable {

    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "user_id")
    private int userId;

    public GenreLikesPK() {
    }

    public GenreLikesPK(int genreId, int userId) {
        this.genreId = genreId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenreLikesPK that = (GenreLikesPK) o;

        return userId == that.userId && genreId == that.genreId;
    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + userId;
        return result;
    }
}
