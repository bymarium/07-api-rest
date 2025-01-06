package com.example.restaurant.services.order;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandModification;
import com.example.restaurant.utils.OrderDetailConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpdateOrder implements ICommandModification<Order, OrderDTO> {
	private final IOrderRepository orderRepository;

	@Autowired
	public UpdateOrder(IOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order execute(Long orderId, OrderDTO orderDTO) {
		return orderRepository.findById(orderId).map(o -> {
			o.setDate(orderDTO.getDate());
			if (o.getOrderDetails() == null) {
				o.setOrderDetails(new ArrayList<>());
			}
			orderDTO.getOrderDetails().forEach(orderDetailDTO -> {
				OrderDetail orderDetail = OrderDetailConverter.convertDtoToEntity(orderDetailDTO);
				o.getOrderDetails().add(orderDetail);
			});
			return orderRepository.save(o);
		}).orElseThrow(() -> new RuntimeException("Pedido con id " + orderId + " no encontrado"));
	}
}