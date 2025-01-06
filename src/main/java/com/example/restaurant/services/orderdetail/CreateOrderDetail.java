package com.example.restaurant.services.orderdetail;

import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderDetailRepository;
import com.example.restaurant.utils.Converters.OrderDetailConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOrderDetail {
	private final IOrderDetailRepository orderDetailRepository;
	private final IDishRepository dishRepository;

	@Autowired
	public CreateOrderDetail(IOrderDetailRepository orderDetailRepository, IDishRepository dishRepository) {
		this.orderDetailRepository = orderDetailRepository;
		this.dishRepository = dishRepository;
	}

	public List<OrderDetail> execute(List<OrderDetailDTO> orderDetails, Long orderId) {
		return orderDetails.stream().map(orderDetailDTO -> {
			OrderDetail orderDetail = OrderDetailConverter.convertDtoToEntity(orderDetailDTO);
			Dish dish = dishRepository.findById(orderDetailDTO.getDishId()).orElseThrow(() -> new RuntimeException("Plato con id " + orderDetailDTO.getDishId() + " no encontrado"));

			Order order = new Order();
			order.setId(orderId);
			orderDetail.setOrder(order);

			Float priceAdjust = dish.applyAdjust();
			orderDetail.setUnitPrice(priceAdjust);
			orderDetail.setSubTotal(priceAdjust * orderDetailDTO.getQuantity());

			return orderDetailRepository.save(orderDetail);
		}).toList();
	}
}