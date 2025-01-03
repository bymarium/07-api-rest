package com.example.restaurant.repositories;

import com.example.restaurant.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
