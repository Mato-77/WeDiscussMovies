package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Genre;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.querymodels.GenreLikes;
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
    private final GenreLikesRepository genreLikesRepository;

    public GenreController(GenreService genreService, GenreLikesRepository genreLikesRepository) {
        this.genreService = genreService;
        this.genreLikesRepository = genreLikesRepository;
    }
    @GetMapping
    public String getGenres(Model model){
        model.addAttribute("genres",genreService.findAllWithLikes());
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("contentTemplate","genres");
        addModelPropertiesForUser(model);
        return "template";
    }

    private void addModelPropertiesForUser(Model model){
        User user = LoggedUser.getLoggedUser();
        List<UserGenres> genreLikesList = this.genreLikesRepository.findAllByUser(user);
        List<Genre> genres = new ArrayList<>();
        for(UserGenres g: genreLikesList){
            genres.add(genreService.findById(g.getId().getGenreId()));
        }
        model.addAttribute("likedGenres",genres);
        model.addAttribute("user",user);
    }
}
