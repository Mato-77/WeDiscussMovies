package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.ajaxmodels.Grade;
import com.wediscussmovies.project.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private final MovieService movieService;


    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        try {
            this.movieService.deleteById(id);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);

        }
    }
    @GetMapping("/like/{movieId}")
    public ResponseEntity likeMovie(@PathVariable Integer movieId, @RequestParam Integer userId){
        try {
            this.movieService.likeMovie(movieId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/unlike/{movieId}")
    public ResponseEntity unlikeMovie(@PathVariable Integer movieId, @RequestParam Integer userId){
        try {
            this.movieService.unlikeMovie(movieId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @PostMapping("/grade/{movieId}")
    public ResponseEntity addGrade(@PathVariable Integer movieId, @RequestBody Grade grade){
        try {
            if(grade.getRating() < 5)
                grade.setRating(5);
            else if(grade.getRating()>10)
                grade.setRating(10);
            this.movieService.addGradeMovie(movieId, LoggedUser.getLoggedUser(),grade);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }

    }

}
