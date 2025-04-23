package com.example.francegrance.controllers.userControllers;

import com.example.francegrance.service.FragranceService;
import com.example.francegrance.service.UserService;
import com.example.francegrance.service.WishItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController{
    private final WishItemService wishService;
    private final UserService userService;
    private final FragranceService fragranceService;
    @GetMapping
    public String userInfo(Model model,Authentication auth){
        model.addAttribute("role",auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        model.addAttribute("user",userService.findByUsername(auth.getName()));
        return "user-info";
    }



    @PostMapping("/add")
    public String processAdd(@RequestParam Long fr, Authentication auth){
        wishService.addWishItem(fragranceService.getFragranceById(fr),auth.getName());
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long id,Authentication authentication){
        wishService.removeFragranceByIdAndUsername(id,authentication.getName());
        return "redirect:/user";
    }

}
