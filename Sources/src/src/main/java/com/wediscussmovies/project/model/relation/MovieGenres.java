package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.primarykeys.MovieGenresPK;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "movie_genres", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class MovieGenres {

    @EmbeddedId
    private MovieGenresPK id;

    @ManyToOne
    @MapsId(value = "genre_id")
    @JoinColumn(name = "genre_id")
    @GraphQLQuery(name = "genre",description = "Жанр")
    private Genre genre;

    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    @GraphQLQuery(name = "movie",description = "Филм")
    private Movie movie;

    public MovieGenres() {
    }

    public MovieGenres(Movie movie, Genre genre) {
        this.id = new MovieGenresPK(movie.getMovieId(),genre.getGenreId());
        this.genre = genre;
        this.movie = movie;
    }





}
