package com.tts.reviewspring.controller;

import com.tts.reviewspring.model.Tweet;
import com.tts.reviewspring.model.User;
import com.tts.reviewspring.service.TweetService;
import com.tts.reviewspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable("username") String username, Model model ) {

        // find user
        User user = userService.findByUsername(username);

        // find tweet from the user
        List<Tweet> tweets = tweetService.findAllByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("tweetList", tweets);

        return "user";

    }

}
