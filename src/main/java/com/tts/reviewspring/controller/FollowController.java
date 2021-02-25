package com.tts.reviewspring.controller;

import com.tts.reviewspring.model.User;
import com.tts.reviewspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FollowController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/follow/{username}")
    public String follow(@PathVariable String username, HttpServletRequest request) {

        // We call UserService to get the currently logged in user and the user we will follow
        User loggedInUser = userService.getLoggedInUser();
        User userToFollow = userService.findByUsername(username);

        List<User> followers = userToFollow.getFollowers();
        followers.add(loggedInUser);
        userToFollow.setFollowers(followers);
        // push changes to the db and redirect to the last page
        userService.save(userToFollow);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping(value = "/unfollow/{username}")
    public String unfollow(@PathVariable(value="username") String username, HttpServletRequest request) {
        User loggedInUser = userService.getLoggedInUser();
        User userToUnFollow = userService.findByUsername(username);
        List<User> followers = userToUnFollow.getFollowers();
        followers.remove(loggedInUser);
        userToUnFollow.setFollowers(followers);
        userService.save(userToUnFollow);
        return "redirect:" + request.getHeader("Referer");
    }

}
