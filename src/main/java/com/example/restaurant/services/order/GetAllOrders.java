package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllOrders implements ICommand<List<Order>> {
	private final IOrderRepository orderRepository;

	@Autowired
	public GetAllOrders(IOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<Order> execute() {
		return orderRepository.findAll();
	}
}