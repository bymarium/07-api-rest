package com.example.restaurant.services.orderdetail;

import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateOrderDetail  {
	private final IDishRepository dishRepository;
	private final IOrderDetailRepository orderDetailRepository;

	public UpdateOrderDetail(IDishRepository dishRepository, IOrderDetailRepository orderDetailRepository) {
		this.dishRepository = dishRepository;
		this.orderDetailRepository = orderDetailRepository;
	}

	public List<OrderDetail> execute(List<OrderDetailDTO> orderDetailsDTO, Long orderId) {
		return orderDetailsDTO.stream().map(orderDetailDTO -> {
			OrderDetail orderDetail = new OrderDetail();
			Order order = new Order();
			order.setId(orderId);
			orderDetail.setOrder(order);
			Dish dish = dishRepository.findById(orderDetailDTO.getDishId())
				.orElseThrow(() -> new RuntimeException("Plato con id " + orderDetailDTO.getDishId() + " no encontrado"));
			orderDetail.setDish(dish);
			orderDetail.setQuantity(orderDetailDTO.getQuantity());
			orderDetail.setUnitPrice(dish.getPrice());
			orderDetail.setSubTotal(orderDetail.getUnitPrice() * orderDetail.getQuantity());
			return orderDetailRepository.save(orderDetail);
		}).toList();
	}
}