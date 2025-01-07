package com.example.restaurant.services.client;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.IObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateTypeClient implements IObserver {
	private final IOrderRepository orderRepository;
	private final IClientRepository clientRepository;

	@Autowired
	public UpdateTypeClient(IOrderRepository orderRepository, IClientRepository clientRepository) {
		this.orderRepository = orderRepository;
		this.clientRepository = clientRepository;
	}

	@Override
	public void update(Order order) {
		Long clientId = order.getClient().getId();
		Long count = orderRepository.countByClient_Id(clientId);

		if (count >= 10) {
			order.getClient().setUserType(Type.FREQUENT.getName());
			order.getClient().setAdjust(0.9762f);
			clientRepository.save(order.getClient());
		}
	}
}