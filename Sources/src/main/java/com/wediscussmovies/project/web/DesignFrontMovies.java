package com.wediscussmovies.project.web;

import com.wediscussmovies.project.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DesignFrontMovies {
    public static  void designMovieList(List<Movie> movieList, List<List<Movie>> movie_rows){
        for(int i=0; i<movieList.size(); i+=4){
            int j = i+4;
            if(j>movieList.size())
                j= movieList.size();
            movie_rows.add(movieList.subList(i, j));
        }
    }
}
