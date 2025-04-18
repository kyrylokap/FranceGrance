package com.example.francegrance.repository;


import com.example.francegrance.models.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem,Long> {
    List<WishItem> findByUserUsername(String username);
}
