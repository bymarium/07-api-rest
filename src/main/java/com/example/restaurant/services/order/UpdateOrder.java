package com.example.restaurant.services.order;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IOrderDetailRepository;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.interfaces.ICommandModification;
import com.example.restaurant.services.orderdetail.UpdateOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateOrder implements ICommandModification<Order, OrderDTO> {
	private final IOrderRepository orderRepository;
	private final UpdateOrderDetail updateOrderDetail;
	private final IOrderDetailRepository orderDetailRepository;

	@Autowired
	public UpdateOrder(IOrderRepository orderRepository, UpdateOrderDetail updateOrderDetail, IOrderDetailRepository orderDetailRepository) {
		this.orderRepository = orderRepository;
		this.updateOrderDetail = updateOrderDetail;
		this.orderDetailRepository = orderDetailRepository;
	}

	@Override
	public Order execute(Long orderId, OrderDTO orderDTO) {
		return orderRepository.findById(orderId).map(o -> {
			orderDetailRepository.deleteAll(o.getOrderDetails());
			o.getOrderDetails().clear();
			List<OrderDetail> updatedDetails = updateOrderDetail.execute(orderDTO.getOrderDetails(), o.getId());
			Order newOrder = orderRepository.save(o);
			Float totalPrice = (float) updatedDetails.stream().mapToDouble(OrderDetail::getSubTotal).sum();
			newOrder.setTotalPrice(totalPrice);
			orderRepository.save(newOrder);
			newOrder.setOrderDetails(updatedDetails);
			return newOrder;
		}).orElseThrow(() -> new RuntimeException("Pedido con id " + orderId + " no encontrado"));
	}
}