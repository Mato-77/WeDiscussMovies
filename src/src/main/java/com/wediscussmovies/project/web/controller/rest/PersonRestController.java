package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonRestController {

    private final PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePerson(@PathVariable Integer id){

        try {
            this.personService.deleteById(id);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @PostMapping("/grade/{personId}")
    public ResponseEntity addGrade(@PathVariable Integer personId, @RequestBody Grade grade){
        try {
            this.personService.addGradePerson(personId, LoggedUser.getLoggedUser(),grade);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }

    }
}
