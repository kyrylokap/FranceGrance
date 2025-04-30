package com.example.francegrance.controllers.noAuthControllers;

import com.example.francegrance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor

public class AuthController{

    private final UserService service;
    @GetMapping("/register")
    public String showRegisterForm(){return "register";}

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmedPassword,
                           RedirectAttributes rd){
        AtomicReference<String> err = new AtomicReference<>("");

        String redirect = service.validateRegister(password, username, confirmedPassword, err);
        rd.addFlashAttribute("err", err.get());
        return redirect;
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
