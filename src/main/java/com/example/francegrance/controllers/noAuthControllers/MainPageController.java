package com.example.francegrance.controllers.noAuthControllers;

import com.example.francegrance.service.FragranceService;
import com.example.francegrance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class MainPageController{
    private final FragranceService fragranceService;
    @GetMapping("/")
    public String mainPage(Model model,Authentication auth){
        model.addAttribute("fragranceName","");
        model.addAttribute("filter",0);

        if(model.getAttribute("fragrances") == null){
            model.addAttribute("fragrances",fragranceService.all());
        }

        if(auth != null){
            model.addAttribute("role",auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "fragranceName")String fragranceName,
                         @RequestParam Long filter,
                         RedirectAttributes rd){
        rd.addFlashAttribute("fragrances",fragranceService.searchFragrances(fragranceName,filter));
        return "redirect:/";
    }

}
