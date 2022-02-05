package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("contentTemplate","genres");
        return "template";
    }
}
