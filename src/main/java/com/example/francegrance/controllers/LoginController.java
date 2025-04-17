package com.example.francegrance.controllers;


import com.example.francegrance.models.User;
import com.example.francegrance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController{
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/all")
    @ResponseBody
    public List<User> getUsers(){
        return userService.findAll();
    }

}
