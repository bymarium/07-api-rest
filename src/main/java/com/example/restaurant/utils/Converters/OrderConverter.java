package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;

import java.time.LocalDate;

public class OrderConverter {
	public static Order convertDtoToEntity(OrderDTO orderDTO) {
		Order order = new Order();
		Client client = new Client();
		client.setId(orderDTO.getClientId());
		order.setClient(client);
		order.setDate(LocalDate.now());
		return order;
	}
}