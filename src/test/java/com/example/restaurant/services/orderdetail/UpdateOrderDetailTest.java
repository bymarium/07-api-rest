package com.example.restaurant.services.orderdetail;

import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Menu;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.repositories.IOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderDetailTest {
	private IOrderDetailRepository mockOrderDetailRepository;
	private IDishRepository mockDishRepository;
	private UpdateOrderDetail updateOrderDetail;

	@BeforeEach
	public void setUp() {
		mockOrderDetailRepository = mock(IOrderDetailRepository.class);
		mockDishRepository = mock(IDishRepository.class);
		updateOrderDetail = new UpdateOrderDetail(mockDishRepository, mockOrderDetailRepository);
	}

	@Test
	void execute() {
		Order order = new Order(1L, 316f);

		Menu menu = mock(Menu.class);
		Dish dish1 = new Dish(1L, "Pollo apanado", "Pollo con papas", 38f);
		Dish dish2 = new Dish(2L, "Pollo asado", "Pollo con papas y miel", 48f);
		dish1.setMenu(menu);
		dish2.setMenu(menu);

		OrderDetailDTO orderDetailDTO1 = new OrderDetailDTO();
		orderDetailDTO1.setDishId(1L);
		orderDetailDTO1.setQuantity(2);

		OrderDetailDTO orderDetailDTO2 = new OrderDetailDTO();
		orderDetailDTO2.setDishId(2L);
		orderDetailDTO2.setQuantity(5);

		List<OrderDetailDTO> orderDetailsDTO = List.of(orderDetailDTO1, orderDetailDTO2);

		OrderDetail orderDetail1 = new OrderDetail(1L, 2, dish1.getPrice());
		OrderDetail orderDetail2 = new OrderDetail(2L, 5, dish2.getPrice());

		List<OrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);

		order.setOrderDetails(orderDetails);

		when(mockDishRepository.findById(1L)).thenReturn(Optional.of(dish1));
		when(mockDishRepository.findById(2L)).thenReturn(Optional.of(dish2));
		when(mockOrderDetailRepository.save(any(OrderDetail.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));

		List<OrderDetail> response = updateOrderDetail.execute(orderDetailsDTO, order.getId());

		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(1L, response.get(0).getDish().getId());
		assertEquals(2, response.get(0).getQuantity());
		assertEquals(38f * 2, response.get(0).getSubTotal());
		assertEquals(2L, response.get(1).getDish().getId());
		assertEquals(5, response.get(1).getQuantity());
		assertEquals(48f * 5, response.get(1).getSubTotal());

		verify(mockDishRepository, times(2)).findById(anyLong());
		verify(mockOrderDetailRepository, times(2)).save(any(OrderDetail.class));
	}
}