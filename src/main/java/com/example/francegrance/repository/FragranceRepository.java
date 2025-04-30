package com.example.francegrance.repository;

import com.example.francegrance.models.Fragrance;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FragranceRepository extends JpaRepository<Fragrance, Long>{
    Fragrance searchFirstByBrandAndCapacityAndPriceAndNameIgnoreCaseAndType(String brand,String capacity,Double price,String name,String type);
    @Query("SELECT f FROM Fragrance f WHERE " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(f.brand) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(f.designer) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Fragrance> searchByAnyField(@Param("query") String query);
    void deleteById(Long id);


    @Transactional
    @Modifying
    @Query("UPDATE Fragrance f  set f.name = :name, f.brand = :brand, f.designer = :designer," +
            "f.type = :type, f.capacity = :capacity, f.price = :price, f.photo = :photo,  f.availableCount = :availableCount " +
            "where f.id = :id")
    void updateFragrance(@Param("id") Long id,
                         @Param("name") String name,
                         @Param("brand") String brand,
                         @Param("designer") String designer,
                         @Param("type") String type,
                         @Param("capacity") String capacity,
                         @Param("price") Double price,
                         @Param("photo") String photo,
                         @Param("availableCount") Long availableCount);

}
