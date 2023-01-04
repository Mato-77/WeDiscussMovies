package com.wediscussmovies.project.model.exception;

public class MovieHasAlreadyDirector extends RuntimeException{

    public MovieHasAlreadyDirector(String title,String name, String surname){
        super("За режисер на филмот " + title + " е поставен " + name + " " + surname);
    }
}
