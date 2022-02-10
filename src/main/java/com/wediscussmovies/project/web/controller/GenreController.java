package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.repository.GenreLikesRepository;
import com.wediscussmovies.project.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping
    public String getGenres(Model model){
        model.addAttribute("genres",genreService.findAllWithLikes());
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("contentTemplate","genres");

        User user = LoggedUser.getLoggedUser();

        if (user != null) {
            model.addAttribute("likedGenres", this.genreService.findAllByUser(user));
            model.addAttribute("user",user);

        }
        return "template";
    }

}
