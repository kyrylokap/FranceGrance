package com.example.francegrance.controllers.adminControllers;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.service.FragranceService;
import com.example.francegrance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController{
    private final FragranceService service;
    private final UserService userService;
    @GetMapping
    public String adminPage(Model model, Authentication auth){
        model.addAttribute("role",auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
        return "adminPage";
    }

    @GetMapping("/add")
    public String addFragrance(Model model){
        model.addAttribute("fragrance",new Fragrance());
        return "add";
    }

    @PostMapping("/add")
    public String processAddingFragrance(@ModelAttribute Fragrance f,RedirectAttributes rd){
        String info = service.addFragrance(f);
        rd.addFlashAttribute("info",info);
        return "redirect:/admin/add";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "name")String name,
                         RedirectAttributes rd){
        rd.addFlashAttribute("fragrances",service.searchFragrances(name,null));
        return "redirect:/admin/view";
    }
    @GetMapping("/view")
    public String findFragrance(Model model){
        model.addAttribute("name","");
        model.addAttribute("id",0L);
        if(!model.containsAttribute("fragrances")){
            model.addAttribute("fragrances",service.all());
        }
        return "view";
    }


    @GetMapping("/update")
    public String update(){
        return "updateFragrance";
    }
    @PostMapping("/update")
    public String postProcessUpdate(@ModelAttribute Fragrance fragrance){
        service.updateFragrance(fragrance);
        return "updateFragrance";
    }

    @PostMapping("/process-update")
    public String processUpdate(@RequestParam Long id,RedirectAttributes rd){
        rd.addFlashAttribute("fragrance",service.getFragranceById(id));
        return "redirect:/admin/update";
    }



    @PostMapping("/removed")
    public String removed(@RequestParam Long id){

        service.deleteFragrance(id);
        return "redirect:/admin/view";
    }


    @GetMapping("/ban")
    public String ban(Model model){
        model.addAttribute("users",userService.getAll());
        return "ban";
    }

    @PostMapping("/ban")
    public String processBan(@RequestParam(name = "id") Long userId){
        userService.deleteUser(userId);
        return "redirect:/admin/ban";
    }


}
