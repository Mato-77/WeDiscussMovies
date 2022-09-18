package com.wediscussmovies.project.model.exception;

public class InvalidArgumentsException extends RuntimeException{
    public InvalidArgumentsException(){
        super("Невалидна најава!");
    }
}
