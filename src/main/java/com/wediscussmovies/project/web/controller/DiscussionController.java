package com.wediscussmovies.project.web.controller;

import com.wediscussmovies.project.LoggedUser;
import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.relation.DiscussionLikes;
import com.wediscussmovies.project.querymodels.DiscussionLikesQM;
import com.wediscussmovies.project.service.DiscussionService;
import com.wediscussmovies.project.service.MovieService;
import com.wediscussmovies.project.service.PersonService;
import com.wediscussmovies.project.service.ReplyService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final ReplyService replyService;
    private final MovieService movieService;
    private final PersonService personService;

    public DiscussionController(DiscussionService discussionService, ReplyService replyService, MovieService movieService, PersonService personService) {
        this.discussionService = discussionService;
        this.replyService = replyService;
        this.movieService = movieService;
        this.personService = personService;
    }

    @GetMapping()
    public String getDiscussions(@RequestParam(required = false) String titleSearch,
                                    Model model){
        /* 1.Validacija i  na frontend so, da ne se isprakja baranjeto, ako titleSearch e null ili empty
         da ne se preoptovaruva serverot so baranja za titleSearch koga e null!

         */
        //List<Discussion> discussions = discussionService.listAllByTitle(titleSearch);
        List<Discussion> discussions = discussionService.listAll();
        model.addAttribute("discussions", discussions);
        model.addAttribute("contentTemplate", "discussionsList");
        model.addAttribute("user",LoggedUser.getLoggedUser());
        this.addModelPropertiesForUser(model);
       // this.addModelPropertiesLikes(model,null, discussions);
        return "template";
    }

    @GetMapping("/{id}")
    public String getDiscussion(
            @PathVariable Integer id,
            Model model){
        /*
            2. Moze da se vrati indikator deka takva diskusija ne postoi! - postaveno
         */
        try {
            Discussion disc = discussionService.findById(id);
            model.addAttribute("disc", disc);
            model.addAttribute("contentTemplate", "discussion");
            model.addAttribute("replies",this.replyService.findAllByDiscussion(disc));
            addModelPropertiesForUser(model);

            return "template";
        }
        catch (RuntimeException exception){
            return "redirect:/discussions?error="+exception.getMessage();

        }
    }
    @GetMapping("/all/{id}")
    public String getDiscussionForId(@PathVariable Integer id,@RequestParam Character type, Model model){

        model.addAttribute("discussions", this.discussionService.findAllForPersonOrMovie(id,type));
        model.addAttribute("contentTemplate", "discussionForType");
        return "template";

    }

    @GetMapping("/{id}/replies")
    public String getRepliesToDiscussion(
            @PathVariable Integer id,
            Model model){

        try {
            Discussion discussion = discussionService.findById(id);
            model.addAttribute("discussion", discussion);
            model.addAttribute("contentTemplate", "discussionReply");
            return "template";
        }
        catch (RuntimeException exception){
            return "redirect:/discussions?error="+exception.getMessage();

        }

    }


    @GetMapping("/add/{id}")
    public String getFormForEdit(Model model,
                                           @PathVariable Integer id)
    {
        Discussion discussion = this.discussionService.findById(id);
        setModelPropertiesForForm(model);
        model.addAttribute("discussion", discussion);

        return "template";
    }
    @GetMapping("/add")
    public String getFormForAdd(Model model)
    {
        setModelPropertiesForForm(model);

        return "template";
    }


    @PostMapping("/save")
    public String saveDiscussion(
           @RequestParam Integer idDiscussed,
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam Character type    ){



        try{

            discussionService.save(type,idDiscussed, title, text, LoggedUser.getLoggedUser());
            return "redirect:/discussions";

        }

        catch (RuntimeException exc){
            return "redirect:/discussions?error="+exc.getMessage();

        }
    }

    @PostMapping("/save/{discussionId}")
    public String editDiscussion(
                                               @PathVariable Integer discussionId,
                                               @RequestParam String title,
                                               @RequestParam String text,
                                               @RequestParam Character type,
                                               @RequestParam Integer idDiscussed
                                               ){




        try{


            discussionService.edit(discussionId,type,idDiscussed, title, text);
            return "redirect:/discussions";

        }

        catch (RuntimeException exc){
            return "redirect:/discussions?error="+exc.getMessage();

        }
    }

    private void setModelPropertiesForForm(Model model){
        model.addAttribute("movies",this.movieService.listAll());
        model.addAttribute("persons",this.personService.findAll());
        model.addAttribute("contentTemplate", "discussionsAdd");

    }
    private void addModelPropertiesForUser(Model model){
        User user = LoggedUser.getLoggedUser();
        model.addAttribute("likedDiscussions",this.discussionService.findLikedDiscussionsByUser(user));
        model.addAttribute("user",user);
    }
    private void addModelPropertiesLikes(Model model, Discussion discussion, List<Discussion> discussions){
        // ispagja kompliciran kod vaka, podobro da dodademe i svojstvo u diskusijata i koga ke se dodade lajk
        // soodvetno da se zgoleme i obratno
//        if(discussion==null){
//            List<DiscussionLikesQM> discussionLikes = new ArrayList<>();
//            for(Discussion d: discussions){
//                discussionLikes.add(discussionService.findLikesForDiscussionWithId(d.getDiscussionId()));
//            }
//            model.addAttribute("likes", discussionLikes);
//        }
//        else{
//            model.addAttribute("likes",discussionService.findLikesForDiscussionWithId(discussion.getDiscussionId()).getLikes());
//        }
    }


}
