package com.wediscussmovies.project.model.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super("Корисничкото име е зафатено " + username);
    }
}
