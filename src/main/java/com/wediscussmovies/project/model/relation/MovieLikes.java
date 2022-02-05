package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.MovieLikesPK;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie_likes", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class MovieLikes {
   @EmbeddedId
   private MovieLikesPK id;

    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    private Movie movie;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    public MovieLikes(Movie movie, User user) {
        this.id = new MovieLikesPK(movie.getMovieId(), user.getUserId());
        this.movie = movie;
        this.user = user;
    }

    public MovieLikes() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieLikes that = (MovieLikes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
