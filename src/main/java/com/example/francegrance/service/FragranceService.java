package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.repository.FragranceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FragranceService {
    private final FragranceRepository repo;

    public String addFragrance(Fragrance f){
        if (repo.searchFirstByBrandAndCapacityAndPriceAndNameIgnoreCaseAndType(
                f.getBrand(),
                f.getCapacity()
                ,f.getPrice(),
                f.getName().toLowerCase()
                ,f.getType()) == null){
            repo.save(f);
            return "Added fragrance!";
        }
        return "Failed to add fragrance, fragrance already exists!";
    }

    public List<Fragrance> getFragranceByName(String name){
        return repo.getFragrancesByNameIgnoreCase(name.toLowerCase());
    }
    public Fragrance getFragranceById(Long id){
        return repo.findById(id).get();
    }
}
