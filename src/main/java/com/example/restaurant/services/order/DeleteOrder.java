package com.example.restaurant.services.order;

import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteOrder implements ICommandParametrized<Void, Long> {
	private final IOrderRepository orderRepository;

	@Autowired
	public DeleteOrder(IOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Void execute(Long orderId) {
		if (!orderRepository.existsById(orderId)) {
			throw new RuntimeException("Pedido con id " + orderId + " no encontrado");
		}

		orderRepository.deleteById(orderId);
		return null;
	}
}