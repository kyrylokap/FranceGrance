package com.example.francegrance.repository;


import com.example.francegrance.models.WishItem;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishItemRepository extends JpaRepository<WishItem,Long>{
    List<WishItem> findByUserUsername(String username);
    Optional<WishItem>findFirstByFragranceIdAndUserUsername(Long id,String username);

    void deleteWishItemsByFragranceId(Long id);



    void deleteWishItemByFragranceIdAndUserUsername(Long fragranceId,String username);
}
