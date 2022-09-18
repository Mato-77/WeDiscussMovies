package com.wediscussmovies.project.model.exception;

public class MovieIdNotFoundException extends RuntimeException{
    public MovieIdNotFoundException(Integer id) {
        super("Movie with id {"+id+"} was not found.");
    }
}
