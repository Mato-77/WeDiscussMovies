package com.wediscussmovies.project.model.primarykeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class UserGenresPK implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "genre_id")
    private int genreId;

    public UserGenresPK() {
    }

    public UserGenresPK(int userId, int genreId) {
        this.userId = userId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGenresPK that = (UserGenresPK) o;



        return userId == that.userId && genreId == that.genreId;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + genreId;
        return result;
    }
}
