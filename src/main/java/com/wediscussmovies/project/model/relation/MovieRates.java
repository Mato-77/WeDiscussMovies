package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.MovieRatesPK;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "movie_rates", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class MovieRates {

    @EmbeddedId
    private MovieRatesPK id;

    @Basic
    @Column(name = "reason")
    private String reason;
    @Basic
    @Column(name = "stars_rated")
    private int starsRated;


    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    private Movie movie;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    public MovieRates() {
    }

    public MovieRates(User user,Movie movie,String reason,Integer starsRated) {
        this.id = new MovieRatesPK(movie.getMovieId(), user.getUserId());
        this.reason = reason;
        this.starsRated = starsRated;
        this.movie = movie;
        this.user = user;
    }
}
