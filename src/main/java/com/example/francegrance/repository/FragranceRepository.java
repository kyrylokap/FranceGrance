package com.example.francegrance.repository;

import com.example.francegrance.models.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FragranceRepository extends JpaRepository<Fragrance, Long>{
    Fragrance searchFirstByBrandAndCapacityAndPriceAndNameIgnoreCaseAndType(String brand,String capacity,Double price,String name,String type);
    List<Fragrance> getFragrancesByNameIgnoreCase(String name);
    Fragrance searchFirstByNameIgnoreCase(String name);
}
