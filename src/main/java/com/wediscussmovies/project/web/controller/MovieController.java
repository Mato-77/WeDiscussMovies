package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.querymodels.MovieLikesQM;
import com.wediscussmovies.project.service.GenreService;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
import com.wediscussmovies.project.web.DesignFrontMovies;
import com.wediscussmovies.project.web.PageFrontMovies;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreService genreService;
    private final PersonService personService;

    public MovieController(MovieService movieService, GenreService genreService, PersonService personService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.personService = personService;
    }

    @GetMapping("/old")
    public String getMoviesOld(@RequestParam(required = false) String titleQuery, Model model,
                            @RequestParam(required = false) String error){
        List<Movie> movies;
        if(titleQuery == null ) {
            movies = movieService.listAll();
        }
        else{
            movies = movieService.searchByTitle(titleQuery);
        }

        addModelPropertiesForUser(model);

        List<List<Movie>> movie_rows = new ArrayList<>();
        DesignFrontMovies.designMovieList(movies,movie_rows);
        model.addAttribute("movies", movies);
        model.addAttribute("movie_rows", movie_rows);
        model.addAttribute("contentTemplate", "moviesList");
        model.addAttribute("genres", genreService.findAll());
        if (error != null && !error.equals(" "))
            model.addAttribute("error",error);
        return "template";
    }


    @GetMapping
    public String getMovies(@RequestParam(required = false) String titleQuery, Model model,
                            @RequestParam(required = false) String error, @RequestParam(required = false) String page){
        if (page==null){
            return "redirect:/movies?page=1";
        }
        addModelPropertiesForUser(model);
        List<Movie> movies = PageFrontMovies.getPagedMovies(page, movieService, model);
        List<List<Movie>> movie_rows = new ArrayList<>();
        DesignFrontMovies.designMovieList(movies,movie_rows);
        //addModelPropertiesForMoviesLikes(model, movies);
        model.addAttribute("movies", movies);
        model.addAttribute("movie_rows", movie_rows);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("contentTemplate", "moviesListPaged");
        if (error != null && !error.equals(" "))
            model.addAttribute("error",error);
        return "template";
    }

    @GetMapping("/{id}")
    public String getMovie(@PathVariable Integer id, Model model){
        model.addAttribute("movie", movieService.findById(id));
        addModelPropertiesForUser(model);
        model.addAttribute("likes", movieService.findLikesForMovieById(id).getLikes());
        model.addAttribute("contentTemplate", "movieShow");
        return "template";
    }

    @GetMapping("/add")
    public String addMovie(Model model){

        addModelProperties(model);
        model.addAttribute("contentTemplate", "moviesAdd");
        return "template";
    }


    @GetMapping("/{id}/delete")
    public String deleteMovie(@PathVariable Integer id){

       try {
           this.movieService.deleteById(id);
           return "redirect:/movies";

       }
       catch (RuntimeException exc){
           return "redirect:/movies?error="+exc.getMessage();
       }
    }

    @PostMapping("/save")
    public String saveMovie(



            @RequestParam String title,
                               @RequestParam String description,
                               @RequestParam String imageUrl,
                               @RequestParam(required = false) Double rating,
                               @RequestParam(required = false) Integer directorId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate airingDate,
                               @RequestParam(required = false)List<Integer> actors,
                               @RequestParam(required = false) List<Integer> genres
                            ){

        try {

            if(rating != null){
                if(rating < 0)
                    rating=0D;
                else if(rating>10){
                    rating=10D;
                }
            }

            Movie movie = this.movieService.save(title,description,imageUrl,airingDate,rating,directorId,actors,genres);

            return "redirect:/movies";
        }
        catch (RuntimeException exc){
            return "redirect:/movies?error="+exc.getMessage();
        }
    }
    @PostMapping("/save/{movieId}")
    public String updateMovie(


            @PathVariable Integer movieId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String imageUrl,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Integer directorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate airingDate,
            @RequestParam List<Integer> actors,
            @RequestParam List<Integer> genres
    ){

        try {

            if(rating != null){
                if(rating < 0)
                    rating=0D;
                else if(rating>10){
                    rating=10D;
                }
            }


            Movie movie = this.movieService.edit(movieId,title,description,imageUrl,airingDate,rating,directorId,actors,genres);

            return "redirect:/movies";
        }
        catch (RuntimeException exc){
            return "redirect:/movies?error="+exc.getMessage();
        }
    }



    @GetMapping("/{id}/edit")
    public String editMovie(@PathVariable Integer id, Model model){
        try {
            Movie movie = movieService.findById(id);
            addModelProperties(model);
            model.addAttribute("movie", movie);
            model.addAttribute("director",movie.getDirector());
            model.addAttribute("movieActors",movieService.findAllActorsForMovie(movie));
            model.addAttribute("movieGenres",movieService.findAllGenresForMovie(movie));
            model.addAttribute("contentTemplate", "moviesAdd");
            return "template";

        }
        catch (RuntimeException exc){
            return "redirect:/movies?error="+exc.getMessage();
        }

    }
    private void addModelProperties(Model model){
        model.addAttribute("directors", personService.findAllDirectors());
        model.addAttribute("actors", personService.findAllActors());
        model.addAttribute("genres", genreService.findAll());
    }
    private void addModelPropertiesForUser(Model model){
        User user = LoggedUser.getLoggedUser();
        model.addAttribute("likedMovies",this.movieService.findLikedMoviesByUser(user));
        model.addAttribute("user",user);
    }
    private void addModelPropertiesForMoviesLikes(Model model, List<Movie> movies){
        HashMap<Integer,MovieLikesQM> movieLikes = new HashMap<>();
        for(Movie m: movies){
            movieLikes.put(m.getMovieId(),movieService.findLikesForMovieById(m.getMovieId()));
        }
        model.addAttribute("movieLikes", movieLikes);
    }

}
