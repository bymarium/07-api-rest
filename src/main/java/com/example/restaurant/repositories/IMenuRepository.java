package com.example.restaurant.repositories;

import com.example.restaurant.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuRepository extends JpaRepository<Menu, Long> {
}