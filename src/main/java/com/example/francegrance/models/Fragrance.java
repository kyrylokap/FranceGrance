package com.example.francegrance.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "fragrance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Fragrance{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private String designer;
    @Column
    private String type;
    @Column
    private String capacity;
    @Column
    private Double price;
    @Column
    private String photo;
    @Column
    private Long availableCount;

    @OneToMany(mappedBy = "fragrance")
    private List<WishItem> wishItems;

    @OneToMany(mappedBy = "fragrance")
    private List<Order> orders;
}
