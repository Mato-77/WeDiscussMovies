package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.service.GenreService;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
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

    @GetMapping
    public String getMovies(@RequestParam(required = false) String titleQuery, Model model,
                            @RequestParam(required = false) String error){
        List<Movie> movies;
        if(titleQuery == null ) {
            movies = movieService.listAll();
        }
        else{
            movies = movieService.searchByTitle(titleQuery);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = (User) userDetails;
          model.addAttribute("likedMovies",this.movieService.findLikedMoviesByUser(user));
          model.addAttribute("user",user);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("contentTemplate", "moviesList");
        if (error != null && !error.equals(" "))
            model.addAttribute("error",error);
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
                               @RequestParam List<Integer> actors,
                               @RequestParam List<Integer> genres
                            ){

        try {
            Movie movie = this.movieService.save(title,description,imageUrl,Date.valueOf(airingDate),rating,directorId,actors,genres);

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
            Movie movie = this.movieService.edit(movieId,title,description,imageUrl,Date.valueOf(airingDate),rating,directorId,actors,genres);

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

        model.addAttribute("directors",  personService.findAllDirectors());
        model.addAttribute("actors", personService.findAllActors());
        model.addAttribute("genres", genreService.findAll());
    }
}
