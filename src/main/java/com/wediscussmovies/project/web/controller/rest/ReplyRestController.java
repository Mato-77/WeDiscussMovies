package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import com.wediscussmovies.project.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/replies")
public class ReplyRestController {

    private final ReplyService replyService;

    public ReplyRestController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/delete/{discussionId}")
    public ResponseEntity deleteById(@RequestParam Integer replyId,@PathVariable Integer discussionId){
        try {
            this.replyService.delete(discussionId,replyId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);

        }
    }
    @GetMapping("/like/{replyId}")
    public ResponseEntity likeReply(@PathVariable Integer replyId,
                                    @RequestParam Integer userId){
        try {
            this.replyService.likeReply(replyId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
    @GetMapping("/unlike/{replyId}")
    public ResponseEntity unlikeReply(@PathVariable Integer replyId,
                                 @RequestParam Integer userId){
        try {
            this.replyService.unlikeReply(replyId,userId);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException exception){
            return ResponseEntity.ok(false);
        }
    }
}
