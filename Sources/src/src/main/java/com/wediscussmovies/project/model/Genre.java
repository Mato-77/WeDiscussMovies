package com.wediscussmovies.project.model;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genres", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id")
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private int genreId;
    @Basic
    @Column(name = "genre_type")
    @GraphQLNonNull
    @GraphQLQuery(name = "type",description = "Име")
    private String genreType;

    public Genre() {

    }

    public Genre(int genreId, String genreType) {
        this.genreId = genreId;
        this.genreType = genreType;
    }

    public Genre(String genreType) {
        this.genreType = genreType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return genreId == genre.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId);
    }
}
