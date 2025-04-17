package com.example.francegrance.controllers.noAuthControllers;

import com.example.francegrance.repository.UserRepository;
import com.example.francegrance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController{

    @Autowired
    private UserService service;

    @GetMapping()
    public String showRegisterForm(){
        return "register";
    }


    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmedPassword){
        if(!password.equals(confirmedPassword) || service.findByUsername(username) != null){
            return "redirect:/register";
        }
        service.save(username,password);
        return "redirect:/login";
    }
}
