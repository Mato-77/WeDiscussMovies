package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import com.wediscussmovies.project.service.ReplyService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/add/{discussionId}")
    public String addForm(@PathVariable Integer discussionId, Model model){
        model.addAttribute("discussionId",discussionId);
        model.addAttribute("contentTemplate","repliesAdd");
        return "template";
    }

    @GetMapping("/edit/{discussionId}/{replyId}")
    public String editForm(@PathVariable Integer discussionId,@PathVariable Integer replyId, Model model){

        try {
            Reply reply = replyService.findById(discussionId,replyId);
            model.addAttribute("reply", reply);
            model.addAttribute("contentTemplate", "repliesAdd");
            return "template";


        }catch (RuntimeException exc){
            return "redirect:/discussions?error="+exc.getMessage();

        }
    }
    @PostMapping("/save")
    public String saveReply(
            @RequestParam Integer discussionId,
            @RequestParam String text){

        try {

            this.replyService.save(discussionId,text, LoggedUser.getLoggedUser());
            return "redirect:/discussions/"+discussionId;

        }
        catch (RuntimeException exc){
            return "redirect:/discussions?error="+exc.getMessage();

        }
    }

    @PostMapping("/save/{replyId}")
    public String editReply(
                            @PathVariable Integer replyId,
                            @RequestParam Integer discussionId,
                            @RequestParam String text){

        /*
            1. Da se realizira so ajax baranje na restController
         */
        try {
            Reply reply = replyService.edit(replyId,discussionId, text);

            return "redirect:/discussions/" + reply.getDiscussion().getDiscussionId();
        }
        catch (RuntimeException exc){
            return "redirect:/discussions?error="+exc.getMessage();
        }

    }

}
