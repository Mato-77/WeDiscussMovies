package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.UserGenresPK;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_genres", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class UserGenres {

    @EmbeddedId
    private UserGenresPK id;

    @ManyToOne
    @MapsId("genre_id")
    @JoinColumn(name = "genre_id")
    @GraphQLQuery(name = "genre",description = "Жанр")
    private Genre genre;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    @GraphQLQuery(name = "user",description = "Корисник")
    private User user;

    public UserGenres() {
    }

    public UserGenres(Genre genre, User user) {
        this.id = new UserGenresPK(user.getUserId(), genre.getGenreId());
        this.genre = genre;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGenres that = (UserGenres) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
