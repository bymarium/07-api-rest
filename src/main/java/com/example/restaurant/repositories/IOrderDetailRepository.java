package com.example.restaurant.repositories;

import com.example.restaurant.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	List<OrderDetail> findByDishId(Long dishId);
}