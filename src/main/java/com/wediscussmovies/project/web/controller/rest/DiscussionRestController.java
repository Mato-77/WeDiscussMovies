package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.service.DiscussionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/discussions")
public class DiscussionRestController {
    private final DiscussionService discussionService;

    public DiscussionRestController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        try {
            this.discussionService.deleteById(id);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);

        }
    }
    @GetMapping("/like/{discussionId}")
    public ResponseEntity likeDiscussion(@PathVariable Integer discussionId, @RequestParam Integer userId){
        try {
            this.discussionService.likeDiscussion(discussionId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/unlike/{discussionId}")
    public ResponseEntity unlikeDiscussion(@PathVariable Integer discussionId, @RequestParam Integer userId){
        try {
            this.discussionService.unlikeDiscussion(discussionId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
}
