package com.example.restaurant.repositories;

import com.example.restaurant.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<Client, Long> {
}