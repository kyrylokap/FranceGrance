package com.example.francegrance.controllers.paymentControllers;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.models.Order;
import com.example.francegrance.service.FragranceService;
import com.example.francegrance.service.OrdersService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final OrdersService ordersService;
    private final FragranceService fragranceService;

    public PaymentController(OrdersService ordersService, FragranceService fragranceService) {
        this.ordersService = ordersService;
        this.fragranceService = fragranceService;
    }


    @PostConstruct
    public void init(){
        Dotenv dotenv = Dotenv.load();
        Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");;
    }


    @PostMapping("/checkout")
    public String checkout(@RequestParam Long orderId) throws StripeException {
        Order order = ordersService.findById(orderId);

        Fragrance fr = fragranceService.getFragranceById(order.getFragrance().getId());
        if(fr.getAvailableCount() < 1){
            return "redirect:/payment-cancel";
        }


        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:9191/payment-success?order_id=" + order.getId())
                .setCancelUrl("http://localhost:9191/payment-cancel?order_id=" + order.getId())
                //.setSuccessUrl("https://3ff2-2a02-a315-41af-9800-f81f-df1c-3a3b-38bf.ngrok-free.app/payment-success?order_id=" + order.getId())
                //.setCancelUrl("https://3ff2-2a02-a315-41af-9800-f81f-df1c-3a3b-38bf.ngrok-free.app/payment-cancel?order_id=" + order.getId())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("pln")
                                                .setUnitAmount(order.getFullPrice())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(order.getFragrance().getName() + " " + order.getFragrance().getBrand())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        return "redirect:" + session.getUrl();
    }
    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("order_id")Long orderId, Model model){
        ordersService.changeStatus(orderId,"Paid");
        model.addAttribute("message","Payment successful!");
        Fragrance fr = ordersService.findById(orderId).getFragrance();
        fr.setAvailableCount(fr.getAvailableCount() - 1);
        fragranceService.updateFragrance(fr);
        return "payment-success";
    }
    @GetMapping("/payment-cancel")
    public String paymentCancel(@RequestParam("order_id")Long orderId,Model model){
        ordersService.changeStatus(orderId,"Cancelled");
        model.addAttribute("message","Payment failed, or no fragrance don't available");
        return "payment-cancel";
    }
}
