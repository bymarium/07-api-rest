package com.example.restaurant.utils;

import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.OrderDetail;

public class OrderDetailConverter {
	public static OrderDetailDTO convertEntityToDto(OrderDetail orderDetail) {
		OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
		orderDetailDTO.setDishId(orderDetail.getDish().getId());
		orderDetailDTO.setQuantity(orderDetail.getQuantity());
		return orderDetailDTO;
	}

	public static OrderDetail convertDtoToEntity(OrderDetailDTO dto) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setQuantity(dto.getQuantity());
		return orderDetail;
	}
}
