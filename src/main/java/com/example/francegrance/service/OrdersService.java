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


    public List<Order> findAllByUserName(String username){
        return repo.findAllByUserId(userRepository.findByUsername(username).getId());
    }
    public List<Order> findAllByFragranceName(String fragranceName){

        return repo.findAllByFragranceName(fragranceName);
    }

    public List<Order> all(){
        return repo.findAll();
    }


    public Order findById(Long id){
        return repo.findById(id).get();
    }

    public void changeStatus(Long orderId,String status){
        repo.updateById(orderId,status);
    }


}
