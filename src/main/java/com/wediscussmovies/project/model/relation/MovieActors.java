package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.primarykeys.MovieActorsPK;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie_actors", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class MovieActors {

    @EmbeddedId
    private MovieActorsPK id;


    @ManyToOne
    @MapsId("movie_id")
    @JoinColumn(name = "movie_id")
    @GraphQLQuery(name = "movie",description = "Филм")
    private Movie movie;


    @ManyToOne
    @MapsId("actor_id")
    @JoinColumn(name = "actor_id")
    @GraphQLQuery(name = "person",description = "Актер")
    private Person person;

    public MovieActors(Movie movie, Person person) {
        this.id = new MovieActorsPK(movie.getMovieId(),person.getPersonId());
        this.movie = movie;
        this.person = person;
    }

    public MovieActors() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieActors that = (MovieActors) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
