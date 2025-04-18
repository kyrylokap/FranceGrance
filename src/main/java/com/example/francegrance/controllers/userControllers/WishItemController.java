package com.example.francegrance.controllers.userControllers;

import com.example.francegrance.service.WishItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class WishItemController{
    private final WishItemService wishService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("fr",0);
        return "addToWishList";
    }

    @PostMapping("/add")
    public String processAdd(@RequestParam Long fr, Authentication auth){
        wishService.addWishItem(wishService.findById(fr),auth.getName());
        return "redirect:/";
    }

    @GetMapping("/wishlist")
    public String showWishList(Model model,Authentication auth){
        model.addAttribute("wishlist",wishService.findByUsername(auth.getName()));
        return "user-wishlist";
    }


}
