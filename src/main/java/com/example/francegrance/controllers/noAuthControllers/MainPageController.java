package com.example.francegrance.controllers.noAuthControllers;

import com.example.francegrance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final UserService userService;
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
}
