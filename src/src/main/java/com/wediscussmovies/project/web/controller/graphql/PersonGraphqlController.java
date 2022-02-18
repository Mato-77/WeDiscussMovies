package com.wediscussmovies.project.web.controller.graphql;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/graphql/person")
public class PersonGraphqlController {


    private final PersonService personService;
    private final MovieService movieService;

    public PersonGraphqlController(PersonService personService, MovieService movieService) {
        this.personService = personService;
        this.movieService = movieService;
    }

    @GetMapping("/actors")
    public String getActors(Model model){
        addModelPropertiesCommon(model);
        model.addAttribute("type","актер");
        model.addAttribute("persons",this.personService.findAllActors());
        String s = "tip";

        return "template";
    }

    @GetMapping("/directors")
    public String getDirectors(Model model){
        addModelPropertiesCommon(model);
        model.addAttribute("type","режисер");
        model.addAttribute("persons",this.personService.findAllDirectors());

        return "template";

    }
    @GetMapping("/add")
    public String getForm(Model model){
        model.addAttribute("contentTemplate","testPersonsAdd");
        model.addAttribute("movies", movieService.findAll());
        return "template";
    }
    @GetMapping("/add/{id}")
    public String getForm(@PathVariable Integer id, Model model, @RequestParam Character type){
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        model.addAttribute("contentTemplate","testPersonsAdd");
        return "template";
    }
    private  void addModelPropertiesCommon(Model model){
        model.addAttribute("user",LoggedUser.getLoggedUser());
        model.addAttribute("contentTemplate","testPersonsList");

    }
}
