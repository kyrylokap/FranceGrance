package com.example.francegrance.controllers.noAuthControllers;

import com.example.francegrance.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@AllArgsConstructor
public class LoginController{
    private final UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
