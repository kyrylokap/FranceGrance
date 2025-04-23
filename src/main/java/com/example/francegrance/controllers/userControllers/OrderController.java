package com.example.francegrance.controllers.userControllers;

import com.example.francegrance.models.Order;
import com.example.francegrance.service.OrdersService;
import com.example.francegrance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class OrderController {
    private final OrdersService service;
    private final UserService userService;

    @GetMapping("/user/orders")
    public String userOrders(Model model,Authentication auth){

        model.addAttribute("orders",service.findAllByUserName(auth.getName()));
        model.addAttribute("role","USER");
        return "orders";
    }

    @PostMapping("/user/orders/order")
    public String placeOrder(@RequestParam Long fragranceId,
                             Model model){

        model.addAttribute("order", new Order());
        model.addAttribute("fragranceId",fragranceId);
        model.addAttribute("phone","");
        model.addAttribute("email","");
        return "order";
    }
    @PostMapping("/user/orders/process-order")
    public String addOrder( @ModelAttribute(name = "order") Order order,
                           @RequestParam("fragranceId") Long fragranceId,
                            @RequestParam String phone,
                            @RequestParam String email,
                            Authentication auth){


        service.addOrder(order,auth.getName(),fragranceId,phone,email);
        userService.addEmailAndPhone(auth.getName(),phone,email);
        return "redirect:/user/orders";
    }


    @GetMapping("/admin/orders")
    public String adminOrders(Model model){
        model.addAttribute("role","ADMIN");
        model.addAttribute("orders",service.all());
        return "orders";
    }

    @PostMapping("/admin/changeStatus")
    public String changeStatus(@RequestParam Long orderId,
                               @RequestParam String status){

        service.changeStatus(orderId,status);
        return "redirect:/admin/orders";
    }


}
