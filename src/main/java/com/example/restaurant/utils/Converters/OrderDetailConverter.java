package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.OrderDetail;

public class OrderDetailConverter {
	public static OrderDetail convertDtoToEntity(OrderDetailDTO dto) {
		OrderDetail orderDetail = new OrderDetail();
		Dish dish = new Dish();
		dish.setId(dto.getDishId());
		orderDetail.setDish(dish);
		orderDetail.setQuantity(dto.getQuantity());
		return orderDetail;
	}
}