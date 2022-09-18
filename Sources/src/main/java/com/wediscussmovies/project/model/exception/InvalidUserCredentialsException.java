package com.wediscussmovies.project.model.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(String username) {
        super("The user with the username {" + username +"} cannot be authenticated.");
    }
}
