package com.tts.reviewspring.controller;

import com.tts.reviewspring.model.Tweet;
import com.tts.reviewspring.model.User;
import com.tts.reviewspring.service.TweetService;
import com.tts.reviewspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;

    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value = "username") String username, Model model) {
/*        // find user
        User user = userService.findByUsername(username);

        // find tweet from the user
        List<Tweet> tweets = tweetService.findAllByUser(user);


        model.addAttribute("user", user);
        model.addAttribute("tweetList", tweets);

        return "user";
    }*/
            // find user
            User loggedInUser = userService.getLoggedInUser();
            User user = userService.findByUsername(username);
            // find tweet from user
            List<Tweet> tweets = tweetService.findAllByUser(user);
            List<User> following = loggedInUser.getFollowing();
            boolean isFollowing = false;
            for (User followedUser : following) {
                if (followedUser.getUsername().equals(username)) {
                    isFollowing = true;
                }
            }
            // add tweets for user
            model.addAttribute("following", isFollowing);
            model.addAttribute("tweetList", tweets);
            model.addAttribute("user", user);
            // return user object
            return "user";
        }

    @GetMapping(value = "/users")
    public String getUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        setTweetCounts(users, model);
        return "users";
    }

    private void setTweetCounts(List<User> users, Model model){
        HashMap<String, Integer> tweetCounts = new HashMap<>();
        for (User user : users){
            List<Tweet> tweets = tweetService.findAllByUser(user);
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);
    }

}
