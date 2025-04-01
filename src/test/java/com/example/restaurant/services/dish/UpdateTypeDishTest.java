package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateTypeDishTest {
	private IDishRepository mockDishRepository;
	private IOrderDetailRepository mockOrderDetailRepository;
	private UpdateTypeDish updateTypeDish;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		mockOrderDetailRepository = mock(IOrderDetailRepository.class);
		updateTypeDish = new UpdateTypeDish(mockOrderDetailRepository, mockDishRepository);
	}

	@Test
	void update() {
		Order order = new Order(1L, 104f);
		Dish dish = new Dish(1L, "Pollo apanado", "Pollo apanado con papas", 38f);
		OrderDetail orderDetail1 = new OrderDetail(1L, 92, 38f);
		orderDetail1.setDish(dish);
		OrderDetail orderDetail2 = new OrderDetail(2L, 15, 48f);
		orderDetail2.setDish(dish);

		List<OrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);
		order.setOrderDetails(orderDetails);

		when(mockOrderDetailRepository.findByDishId(anyLong())).thenReturn(orderDetails);
		when(mockDishRepository.findById(1L)).thenReturn(Optional.of(dish));

		updateTypeDish.update(order);

		verify(mockOrderDetailRepository, times(2)).findByDishId(anyLong());
		verify(mockDishRepository, times(2)).findById(anyLong());
		verify(mockDishRepository, times(2)).save(dish);
	}

	@Test
	void updateWhenNoCount() {
		Order order = new Order(1L, 104f);
		Dish dish = new Dish(1L, "Pollo apanado", "Pollo apanado con papas", 38f);
		OrderDetail orderDetail1 = new OrderDetail(1L, 9, 38f);
		orderDetail1.setDish(dish);
		OrderDetail orderDetail2 = new OrderDetail(2L, 5, 48f);
		orderDetail2.setDish(dish);

		List<OrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);
		order.setOrderDetails(orderDetails);

		when(mockOrderDetailRepository.findByDishId(1L)).thenReturn(orderDetails);

		updateTypeDish.update(order);

		verify(mockOrderDetailRepository, times(2)).findByDishId(anyLong());
		verify(mockDishRepository, never()).findById(anyLong());
		verify(mockDishRepository, never()).save(any(Dish.class));
	}
}