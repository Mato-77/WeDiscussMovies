package com.wediscussmovies.project.web;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.service.MovieService;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class PageFrontMovies {
    public static List<Movie> getPagedMovies(String page, MovieService movieService, Model model){
        List<Integer> movieIds = movieService.listAllIds();
        List<Movie> movies = new ArrayList<>();
        if(page==null || Integer.parseInt(page) <= 0)
            page="1";
        int pageToLoad = Integer.parseInt(page);

        int from = (pageToLoad-1)*12;
        int to = pageToLoad*12;

        if (from>movieIds.size()){
            page = "1";
            pageToLoad = Integer.parseInt(page);
            from = (pageToLoad-1)*12;
            to = pageToLoad*12;
        }
        else if(to>movieIds.size()){
            to = movieIds.size();
        }

        movieIds = movieIds.subList(from, to);

        for(Integer id: movieIds){
            movies.add(movieService.findById(id));
        }
        model.addAttribute("page", page);
        return movies;
    }
}
