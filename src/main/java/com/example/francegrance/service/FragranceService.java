package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.repository.FragranceRepository;
import com.example.francegrance.repository.OrdersRepository;
import com.example.francegrance.repository.WishItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FragranceService {
    private final FragranceRepository repo;

    public String addFragrance(Fragrance f) {
        if (repo.searchFirstByBrandAndCapacityAndPriceAndNameIgnoreCaseAndType(
                f.getBrand(),
                f.getCapacity()
                , f.getPrice(),
                f.getName().toLowerCase()
                , f.getType()) == null) {
            repo.save(f);
            return "Added fragrance!";
        }
        return "Failed to add fragrance, fragrance already exists!";
    }


    public Fragrance getFragranceById(Long id) {
        return repo.findById(id).get();
    }

    public List<Fragrance> all() {
        List<Fragrance> fragrances = repo.findAll();
        Collections.shuffle(fragrances);
        return fragrances;
    }


    public List<Fragrance> searchFragrances(String fragranceName, Long filter) {
        List<Fragrance> result;
        if (fragranceName != null && !fragranceName.isEmpty()) {
            result = repo.searchByAnyField(fragranceName);
        } else {
            result = repo.findAll();
        }
        if (filter != null) {
            switch (filter.intValue()) {
                case 3:
                    result = result.stream()
                            .filter(f -> "Eau de Toilette".equalsIgnoreCase(f.getType()))
                            .collect(Collectors.toList());
                    break;
                case 4:
                    result = result.stream()
                            .filter(f -> "Eau de Perfume".equalsIgnoreCase(f.getType()))
                            .collect(Collectors.toList());
                    break;
                case 5:
                    result = result.stream()
                            .filter(f -> "Perfume".equalsIgnoreCase(f.getType()))
                            .collect(Collectors.toList());
                    break;
            }
        }

        if (filter != null) {
            switch (filter.intValue()) {
                case 1:
                    result = result.stream()
                            .sorted(Comparator.comparing(Fragrance::getPrice))
                            .collect(Collectors.toList());
                    break;
                case 2:
                    result = result.stream()
                            .sorted(Comparator.comparing(Fragrance::getPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                case 6:
                    result = result.stream()
                            .sorted(Comparator.comparing(f -> Integer.parseInt(f.getCapacity().replace("ml", ""))))
                            .collect(Collectors.toList());
                    break;
                case 0:
                    Collections.shuffle(result);
                    break;
            }
        }
        return result;
    }

    @Transactional
    public void deleteFragrance(Long id){

        if(repo.existsById(id)){
            if(!getFragranceById(id).getOrders().isEmpty()){
                return;
            }

            repo.deleteById(id);
        }
    }

    @Transactional
    public void updateFragrance(Fragrance f){repo.save(f);}
}
