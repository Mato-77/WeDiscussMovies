package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.service.DiscussionService;
import com.wediscussmovies.project.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/genres")
public class GenreRestController {
    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/like/{genreId}")
    public ResponseEntity likeGenre(@PathVariable Integer genreId, @RequestParam Integer userId){
        try {
            this.genreService.likeGenre(genreId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/unlike/{genreId}")
    public ResponseEntity unlikeGenre (@PathVariable Integer genreId, @RequestParam Integer userId){
        try {
            this.genreService.unlikeGenre(genreId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(false);
        }
    }
}
