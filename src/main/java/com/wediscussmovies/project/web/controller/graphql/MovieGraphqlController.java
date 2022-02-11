package com.wediscussmovies.project.web.controller.graphql;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("graphql/movie")
public class MovieGraphqlController {

    @GetMapping
    public String getMovies(@RequestParam(required = false) String titleQuery, Model model,
                            @RequestParam(required = false) String error, @RequestParam(required = false) String page){
        model.addAttribute("contentTemplate","testMoviesList");
      return "template";
    }
}
