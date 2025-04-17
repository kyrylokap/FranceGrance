package com.example.francegrance.repository;


import com.example.francegrance.models.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishItem,Long> {

}
