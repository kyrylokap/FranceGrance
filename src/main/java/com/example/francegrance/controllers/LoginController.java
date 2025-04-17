    package com.example.francegrance.controllers;

    import com.example.francegrance.service.UserService;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;
    @Controller
    public class LoginController{
        @Autowired
        private UserService userService;
        @GetMapping("/login")
        public String login(){
            return "login";
        }


    }
