package com.wediscussmovies.project.model.exception;

public class GenreNotExistException extends RuntimeException {
    public GenreNotExistException(Integer id){
        super("Жанрот со дадениот идентификатор " + id + " не постои!");
    }
}
