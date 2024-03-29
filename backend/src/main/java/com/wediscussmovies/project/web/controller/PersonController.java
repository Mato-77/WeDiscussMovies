package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PersonController {
    private final PersonService personService;
    private final MovieService movieService;

    public PersonController(PersonService personService, MovieService movieService) {
        this.personService = personService;
        this.movieService = movieService;
    }

    /*
    1. Prebaruvanjeto po ime i prezime posoodvetno e da bide na frontend
        Da ne se preoptovaruva server i klient da ceka koga moze vo browser vekje dobieni da se filtrirat
     */
    @GetMapping("/actors")
    public String getActors(Model model, @RequestParam(required = false) String nameAndSurname){
        List<Person> persons = personService.findPersonsByNameOrSurname('A',nameAndSurname);
        model.addAttribute("persons", persons);
        model.addAttribute("contentTemplate", "personsList");

        return "template";
    }
    /*
      2. Prebaruvanjeto po ime i prezime posoodvetno e da bide na frontend
          Da ne se preoptovaruva server i klient da ceka koga moze vo browser vekje dobieni da se filtrirat
       */
    @GetMapping("/directors")
    public String getDirectors(Model model, @RequestParam(required = false) String nameAndSurname){
        List<Person> persons = personService.findPersonsByNameOrSurname('D',nameAndSurname);
        model.addAttribute("persons", persons);
        model.addAttribute("contentTemplate", "personsList");
        return "template";
    }

    @GetMapping("/persons/{id}")
    public String getPerson(@PathVariable Integer id, Model model){
        Person person = personService.findById(id);
        //Error handling, could be null!!!!!!!!!
        model.addAttribute("person", person);
        addModelPropertiesForUser(model);
        model.addAttribute("contentTemplate", "personShow");
        return "template";
    }

    @GetMapping("/persons/add")
    public String addingFormForPerson( Model model){

        addModelPropertiesForForm(model);
        return "template";
    }
    @GetMapping("persons/edit/{personId}")
    public String editPerson(@PathVariable Integer personId, Model model){

        try {
            Person person = this.personService.findById(personId);
            addModelPropertiesForForm(model);
            model.addAttribute("person",person);
            model.addAttribute("movieActors",this.personService.findAllMoviesByPerson(person));
            return "template";
        }
        catch (RuntimeException exc){
            return "redirect:/actors?erorr="+exc.getMessage();
        }
    }

    @PostMapping("/persons/save")
    public String savePerson(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Character type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam String imageUrl,
            @RequestParam String description,
            @RequestParam(required = false) List<Integer> movieIds){

        String returnedUrl = getReturnedUrl(type);

        try {

            Person person = this.personService.save(name, surname, type, birthDate, imageUrl, description, movieIds);
           return returnedUrl;
        }
        catch (RuntimeException exc){
            return returnedUrl + "?error" + exc.getMessage();
        }

    }
    @PostMapping("/persons/save/{personId}")
    public String editPerson(
            @PathVariable Integer personId,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Character type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam String imageUrl,
            @RequestParam String description,
            @RequestParam(required = false) List<Integer> movieIds){

        String returnedUrl = getReturnedUrl(type);
        try {

            Person person = this.personService.edit(personId,name, surname, type, birthDate, imageUrl, description, movieIds);
            return returnedUrl;
        }
        catch (RuntimeException exc){
            return returnedUrl + "?error" + exc.getMessage();
        }

    }
    private String getReturnedUrl(Character type){
        if (type.equals('A'))
           return   "redirect:/actors";
        return "redirect:/directors";
    }
    private void addModelPropertiesForForm(Model model){
        model.addAttribute("contentTemplate", "personsAdd");
        model.addAttribute("moviesActors",this.movieService.listAllByType('A'));
        model.addAttribute("moviesDirectors",this.movieService.listAllByType('D'));

    }

    private void addModelPropertiesForUser(Model model){
        User user = LoggedUser.getLoggedUser();
        model.addAttribute("user",user);
    }


}
