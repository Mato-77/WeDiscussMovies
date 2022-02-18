package com.wediscussmovies.project.model.exception;

public class DiscussionNotExistException extends RuntimeException{
    public DiscussionNotExistException(Integer id){
        super("Дискусијата со број " + id +" не постои!");
    }
}
