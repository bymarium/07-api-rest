package com.example.restaurant.services.dish;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderDetailRepository;
import com.example.restaurant.services.interfaces.IObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateTypeDish implements IObserver {
	private final IOrderDetailRepository orderDetailRepository;
	private final IDishRepository dishRepository;

	@Autowired
	public UpdateTypeDish(IOrderDetailRepository orderDetailRepository, IDishRepository dishRepository) {
		this.orderDetailRepository = orderDetailRepository;
		this.dishRepository = dishRepository;
	}

	@Override
	public void update(Order order) {
		order.getOrderDetails()
			.stream()
			.map(orderDetail -> orderDetail.getDish().getId())
			.forEach(dishId -> {
				int totalQuantity = orderDetailRepository.findByDishId(dishId).stream().mapToInt(OrderDetail::getQuantity).sum();
				if (totalQuantity >= 100) {
					Dish dish = dishRepository.findById(dishId).get();
					dish.setId(dishId);
					dish.setDishType(Type.POPULAR.getName());
					dish.setAdjust(1.0573f);
					dishRepository.save(dish);
				}
			});
	}
}