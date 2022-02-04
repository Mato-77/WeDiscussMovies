package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.User;

import java.util.List;

public interface DiscussionService {
    List<Discussion> listAll();
    List<Discussion> listAllByTitle(String title);
    Discussion findById(Integer id);
    void save(Character type,Integer id, String title, String text, User user);
    void edit(Integer discussionId,Character type,Integer id, String title, String text);
    void deleteById(Integer discussionId);
    void likeDiscussion(Integer discussionId,Integer userId);
    void unlikeDiscussion(Integer discussionId,Integer userId);
    List<Discussion> findAllForPersonOrMovie(Integer id,Character type);
}
