package com.example.restaurant.repositories;

import com.example.restaurant.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<Dish, Long> {
}