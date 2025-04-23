package com.example.francegrance.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String city;
    private String street;
    private String postalCode;
    private String building;

    @ManyToOne
    private Fragrance fragrance;

    private String name;
    private String surname;

    @ManyToOne
    private User user;
    private String created_at;
    private String status;

    public Long getFullPrice(){
        return fragrance.getPrice().longValue() * 100;
    }
}
