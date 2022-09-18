package com.wediscussmovies.project.model.exception;

public class PersonNotExistException extends RuntimeException{
    public PersonNotExistException(Integer id){
        super("Личност со број " + id + " не постои!");
    }
}
