package com.wediscussmovies.project.web.controller.graphql;


import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.service.GenreService;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.web.DesignFrontMovies;
import com.wediscussmovies.project.web.PageFrontMovies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/graphql/movie")
public class MovieGraphqlController {

    private final MovieService movieService;
    private final GenreService genreService;

    public MovieGraphqlController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getMovies(@RequestParam(required = false) String titleQuery, Model model,
                            @RequestParam(required = false) String error, @RequestParam(required = false) String page){
//        if (page==null){
//            return "redirect:/movies?page=1";
//        }
        addModelPropertiesForUser(model);
        List<Movie> movies = PageFrontMovies.getPagedMovies(page, movieService, model);
        List<List<Movie>> movie_rows = new ArrayList<>();
        DesignFrontMovies.designMovieList(movies,movie_rows);
        //addModelPropertiesForMoviesLikes(model, movies);
        model.addAttribute("movies", movies);
        model.addAttribute("movie_rows", movie_rows);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("contentTemplate", "testMoviesList");
        if (error != null && !error.equals(" "))
            model.addAttribute("error",error);
        return "template";
    }
    @GetMapping("/add")
    public String getForm(Model model){
        model.addAttribute("contentTemplate","testMoviesAdd");
        return "template";
    }
    @GetMapping("/add/{id}")
    public String getForm(@PathVariable Integer id, Model model){
        model.addAttribute("id",id);
        model.addAttribute("contentTemplate","testMoviesAdd");
        return "template";
    }

    private void addModelPropertiesForUser(Model model){
        User user = LoggedUser.getLoggedUser();
        model.addAttribute("likedMovies",this.movieService.findLikedMoviesByUser(user));
        model.addAttribute("user",user);
    }
}
