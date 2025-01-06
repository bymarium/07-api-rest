package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Client;
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

	public static Order convertDtoToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		Client client = new Client();
		client.setId(orderDTO.getClientId());
		order.setClient(client);
		order.setDate(orderDTO.getDate());
		return order;
	}
}