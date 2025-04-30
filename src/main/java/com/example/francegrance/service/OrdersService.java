package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.models.Order;
import com.example.francegrance.models.User;
import com.example.francegrance.models.WishItem;
import com.example.francegrance.repository.FragranceRepository;
import com.example.francegrance.repository.OrdersRepository;
import com.example.francegrance.repository.UserRepository;
import com.example.francegrance.repository.WishItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {
    private final OrdersRepository repo;
    private WishItemRepository wishItemRepository;
    private final UserRepository userRepository;
    private final FragranceRepository fragranceRepository;
    @Transactional
    public void addOrder(Order order,String username,Long fragranceId,String phone,String email){

        User user = userRepository.findByUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        order.setUser(user);
        userRepository.save(user);
        order.setFragrance(fragranceRepository.findById(fragranceId).get());
        order.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        order.setStatus("Processing");
        repo.save(order);

        wishItemRepository.deleteWishItemByFragranceIdAndUserUsername(fragranceId,username);
    }



    public Order findById(Long id){
        return repo.findById(id).get();
    }

    public void changeStatus(Long orderId,String status){
        repo.updateById(orderId,status);
    }

    public List<Order> allRecently(String username) {
        List<Order> orders = repo.findAll();
        if(username != null)orders = repo.findAllByUserId(userRepository.findByUsername(username).getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        orders = orders.stream()
                .sorted((a, b) -> {
                    LocalDateTime dateA = LocalDateTime.parse(a.getCreated_at(), formatter);
                    LocalDateTime dateB = LocalDateTime.parse(b.getCreated_at(), formatter);
                    return dateB.compareTo(dateA);
                })
                .collect(Collectors.toList());

        return orders;
    }

}
