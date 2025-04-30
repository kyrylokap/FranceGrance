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
    private final UserRepository userRepo;
    private final FragranceRepository fragranceRepository;
    @Transactional
    public void addWishItem(Long fragranceId, String username){
        WishItem wishItem = new WishItem();
        wishItem.setFragrance(fragranceRepository.findById(fragranceId).get());
        wishItem.setUser(userRepo.findByUsername(username));
        repo.save(wishItem);
    }

    @Transactional
    public void removeFragranceByIdAndUsername(Long fragranceId, String username) {
        Optional<WishItem> wishItemOpt = repo.findFirstByFragranceIdAndUserUsername(fragranceId, username);
        wishItemOpt.ifPresent(repo::delete);
    }
}
