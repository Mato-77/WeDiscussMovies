package com.wediscussmovies.project.model.exception;

public class UserWithUsernameAlreadyExists extends RuntimeException{
    public UserWithUsernameAlreadyExists(String username) {
        super("User with the username " + username + " is already registered");
    }
}
