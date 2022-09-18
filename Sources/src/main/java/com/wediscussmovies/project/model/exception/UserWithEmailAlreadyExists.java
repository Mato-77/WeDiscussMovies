package com.wediscussmovies.project.model.exception;

public class UserWithEmailAlreadyExists extends RuntimeException{
    public UserWithEmailAlreadyExists(String email) {
        super("User with the email {" + email +"} is already registered");
    }
}
