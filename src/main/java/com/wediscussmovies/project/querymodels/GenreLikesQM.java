package com.wediscussmovies.project.querymodels;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import java.util.Objects;

@Data
public class GenreLikesQM {
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private Integer genreId;
    @GraphQLQuery(name = "name",description = "Име")
    private String name;
    @GraphQLQuery(name = "likes",description = "Број на лајкови")
    private Long likes;

    //public static Comparator<GenreLikes> sorter = Comparator.comparing(GenreLikes::getLikes).thenComparing(GenreLikes::getName).reversed();

    public GenreLikesQM(Integer genreId, String name, Long likes) {
        this.genreId = genreId;
        this.name = name;
        this.likes = likes;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreLikesQM that = (GenreLikesQM) o;
        return Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId);
    }
}
