package com.wediscussmovies.project.querymodels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Comparator;
import java.util.Objects;

@Data
public class GenreLikes {
    private Integer genreId;
    private String name;
    private Long likes;

    public static Comparator<GenreLikes> sorter = Comparator.comparing(GenreLikes::getLikes).thenComparing(GenreLikes::getName).reversed();

    public GenreLikes(Integer genreId, String name, Long likes) {
        this.genreId = genreId;
        this.name = name;
        this.likes = likes;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreLikes that = (GenreLikes) o;
        return Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId);
    }
}
