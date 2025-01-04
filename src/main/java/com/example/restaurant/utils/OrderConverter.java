package com.example.restaurant.utils;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Order;

public class OrderConverter {
	public static OrderDTO convertEntityToDto(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setClientId(order.getClient().getId());
		orderDTO.setDate(order.getDate());
		orderDTO.setOrderDetails(order.getOrderDetails().stream()
				.map(OrderDetailConverter::convertEntityToDto)
				.toList());
		return orderDTO;
	}
}
