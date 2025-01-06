package com.example.restaurant.repositories;

import com.example.restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
	Long countByClient_Id(Long clientId);
}