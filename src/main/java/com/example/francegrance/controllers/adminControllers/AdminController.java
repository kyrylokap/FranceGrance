package com.example.francegrance.controllers.adminControllers;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.service.FragranceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController{
    private final FragranceService service;
    @GetMapping
    public String adminPage(){
        return "adminPage";
    }

    @GetMapping("/add")
    public String addFragrance(Model model/*, RedirectAttributes rd*/){
        model.addAttribute("fragrance",new Fragrance());
        return "add";
    }

    @PostMapping("/add")
    public String processAddingFragrance(@ModelAttribute Fragrance f,RedirectAttributes rd){
        String info = service.addFragrance(f);
        rd.addFlashAttribute("info",info);
        return "redirect:/admin/add";
    }

    @GetMapping("/findFragrance")
    public String findFragrance(Model model){
        model.addAttribute("name","");
        return "find";
    }

    @PostMapping("/findFragrance")
    public String processFindFragrance(@RequestParam(name = "name") String name,RedirectAttributes rd){
        rd.addFlashAttribute("fragrances",service.getFragranceByName(name));
        System.out.println(name);
        return "redirect:/admin/findFragrance";
    }
}
