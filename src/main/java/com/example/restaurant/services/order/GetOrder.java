package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOrder implements ICommandParametrized<Order, Long> {
	private final IOrderRepository orderRepository;

	@Autowired
	public GetOrder(IOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order execute(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Pedido con id " + orderId + " no encontrado"));
	}
}