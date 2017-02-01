package com.scheduler.controller;

import com.scheduler.data.User;
import com.scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shantanu on 13/11/16.
 */
@RestController
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register")
    public String register(@RequestParam("fullname") String fullname, @RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password) {


        userRepository.save(new User(fullname, email, username, password));

        return "success";
    }
}

