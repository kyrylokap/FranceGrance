package com.example.francegrance.controllers;

import com.example.francegrance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
}
