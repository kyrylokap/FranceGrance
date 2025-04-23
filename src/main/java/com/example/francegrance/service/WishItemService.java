package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.models.WishItem;
import com.example.francegrance.repository.FragranceRepository;
import com.example.francegrance.repository.UserRepository;
import com.example.francegrance.repository.WishItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishItemService {
    private final WishItemRepository repo;
    private final FragranceRepository frRepo;
    private final UserRepository userRepo;
    @Transactional
    public void addWishItem(Fragrance fr, String username){
        WishItem wishItem = new WishItem();
        wishItem.setFragrance(fr);
        wishItem.setUser(userRepo.findByUsername(username));
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

    @Transactional
    public void removeFragranceByIdAndUsername(Long fragranceId, String username) {
        Optional<WishItem> wishItemOpt = repo.findFirstByFragranceIdAndUserUsername(fragranceId, username);
        wishItemOpt.ifPresent(repo::delete);
    }
}
