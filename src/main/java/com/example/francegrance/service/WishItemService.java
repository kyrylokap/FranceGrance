package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.models.WishItem;
import com.example.francegrance.repository.FragranceRepository;
import com.example.francegrance.repository.UserRepository;
import com.example.francegrance.repository.WishItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WishItemService {
    private final WishItemRepository repo;
    private final FragranceRepository frRepo;
    private final UserRepository userRepo;
    public void addWishItem(Fragrance fr, String username){
        WishItem wishItem = new WishItem();
        wishItem.setFragrance(fr);
        wishItem.setUser(userRepo.findByUsername(username));
        System.out.println(wishItem);
        repo.save(wishItem);
    }
    public List<WishItem> findByUsername(String username){
        return userRepo.findByUsername(username).getWishItemList();
    }
    public Fragrance findByName(String name){
        return frRepo.searchFirstByNameIgnoreCase(name.toLowerCase());
    }

    public Fragrance findById(Long id){
        return frRepo.findById(id).get();
    }

    public boolean fragranceExists(Long id){
        return frRepo.existsById(id);
    }
}
