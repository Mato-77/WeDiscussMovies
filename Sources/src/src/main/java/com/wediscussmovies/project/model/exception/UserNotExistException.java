package com.wediscussmovies.project.model.exception;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String username){
        super("Корисник со корисничко име " + username + " не постои!");
    }
}
