package com.example.francegrance.controllers.userControllers;

import com.example.francegrance.service.FragranceService;
import com.example.francegrance.service.UserService;
import com.example.francegrance.service.WishItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController{
    private final WishItemService wishService;
    private final UserService userService;
    @GetMapping
    public String userInfo(Model model,Authentication auth){
        model.addAttribute("role",auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        model.addAttribute("user",userService.findByUsername(auth.getName()));
        return "user";
    }

    @PostMapping("/add")
    public String processAdd(@RequestParam Long fr, Authentication auth){
        wishService.addWishItem(fr,auth.getName());
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long id,Authentication authentication){
        wishService.removeFragranceByIdAndUsername(id,authentication.getName());
        return "redirect:/user";
    }

    @PostMapping("/change-username")
    public String changeUsername(@RequestParam(name = "username")String username,
                                 @RequestParam(name = "userId") Long userId
                                ,HttpServletRequest request, HttpServletResponse response,Authentication auth){

        if(!userService.changeUsername(userId,username)){
            return "redirect:/user";
        }
        SecurityContextLogoutHandler logout = new SecurityContextLogoutHandler();
        logout.logout(request,response,auth);
        return "redirect:/";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name = "password")String password,
                                 @RequestParam(name = "userId") Long userId, Authentication auth,
                                 HttpServletRequest request, HttpServletResponse response){
        userService.changePassword(userId,password);
        SecurityContextLogoutHandler logout = new SecurityContextLogoutHandler();
        logout.logout(request,response,auth);
        return "redirect:/";
    }

}











