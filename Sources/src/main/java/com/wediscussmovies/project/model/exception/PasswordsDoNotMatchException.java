package com.wediscussmovies.project.model.exception;

public class PasswordsDoNotMatchException extends RuntimeException{
    public PasswordsDoNotMatchException(){
        super("Лозинките не се совпаѓаат!");
    }
}
