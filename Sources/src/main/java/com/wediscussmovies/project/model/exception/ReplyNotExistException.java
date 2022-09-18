package com.wediscussmovies.project.model.exception;

import com.wediscussmovies.project.model.primarykeys.ReplyPK;

public class ReplyNotExistException extends RuntimeException{
    public ReplyNotExistException(ReplyPK id){
        super("Репликата со број " + id + " не постои!");
    }
}
