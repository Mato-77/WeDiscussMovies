package com.wediscussmovies.project.querymodels;

import lombok.Data;

@Data
public class GenreLikes {

    private String name;
    private Long likes;

    public GenreLikes(String name, Long likes) {
        this.name = name;
        this.likes = likes;
    }
}
