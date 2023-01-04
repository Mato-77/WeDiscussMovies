package com.wediscussmovies.project.model.exception;

public class PasswordsDontMatchException extends RuntimeException{
    public PasswordsDontMatchException() {
        super("The passwords you provided did not match.");
    }
}
