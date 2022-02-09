package com.wediscussmovies.project.web.controller;


import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.exception.InvalidArgumentsException;
import com.wediscussmovies.project.model.exception.PasswordsDoNotMatchException;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.UserService;
import com.wediscussmovies.project.web.DesignFrontMovies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class UsersController{
    private  final UserService userService;
    private final MovieService movieService;

    public UsersController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String email,
                           @RequestParam String name,
                           @RequestParam String surname) {
        try{
            this.userService.register(email,username,password,repeatedPassword,name,surname);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
    @GetMapping("/profiles/{id}")
    public String getProfilePage(@PathVariable Integer id, Model model){
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("contentTemplate","usersShow");
        return "template";
    }
    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        addModelError(model,error);
        model.addAttribute("contentTemplate","register");
        return "template";
    }
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error,Model model){
        addModelError(model,error);
        model.addAttribute("contentTemplate","login");
        return "template";
    }

    private void addModelError(Model model,String error){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
    }
    @GetMapping("/favoriteList")
    public String getFavoriteList(Model model){
        List<Movie> movieList = this.movieService.findLikedMoviesByUser(LoggedUser.getLoggedUser());
        List<List<Movie>> movie_rows = new ArrayList<>();
        DesignFrontMovies.designMovieList(movieList,movie_rows);
        model.addAttribute("movie_rows", movie_rows);
        model.addAttribute("contentTemplate","favoriteList");
        return "template";

    }


}
