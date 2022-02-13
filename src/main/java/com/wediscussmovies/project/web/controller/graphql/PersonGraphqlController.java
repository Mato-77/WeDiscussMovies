package com.wediscussmovies.project.web.controller.graphql;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/graphql/person")
public class PersonGraphqlController {


    private final PersonService personService;

    public PersonGraphqlController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/actors")
    public String getActors(Model model){
        addModelPropertiesCommon(model);
        model.addAttribute("type","актер");
        model.addAttribute("persons",this.personService.findAllActors());
        return "template";
    }

    @GetMapping("/directors")
    public String getDirectors(Model model){
        addModelPropertiesCommon(model);
        model.addAttribute("type","режисер");
        model.addAttribute("persons",this.personService.findAllDirectors());

        return "template";

    }
    private  void addModelPropertiesCommon(Model model){
        model.addAttribute("user",LoggedUser.getLoggedUser());
        model.addAttribute("contentTemplate","testPersonsList");

    }
}
