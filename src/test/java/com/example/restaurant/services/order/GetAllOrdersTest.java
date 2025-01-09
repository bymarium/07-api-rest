package com.example.restaurant.services.order;

import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllOrdersTest {
	private IOrderRepository mockOrderRepository;
	private GetAllOrders getAllOrders;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		getAllOrders = new GetAllOrders(mockOrderRepository);
	}

	@Test
	void execute() {
		Client client = mock(Client.class);
		List<OrderDetail> orderDetails = List.of(
			new OrderDetail(1L, 2, 38f),
			new OrderDetail(3L, 5, 48f)
		);
		Order order1 = new Order(1L, 120f);
		order1.setClient(client);
		order1.setOrderDetails(orderDetails);
		Order order2 = new Order(2L, 130f);
		order2.setClient(client);
		order2.setOrderDetails(orderDetails);
		Order order3 = new Order(3L, 140f);
		order3.setClient(client);
		order3.setOrderDetails(orderDetails);

		List<Order> orders = List.of(order1, order2, order3);

		when(mockOrderRepository.findAll()).thenReturn(orders);

		List<Order> result = getAllOrders.execute();

		assertEquals(order1.getId(), result.get(0).getId());
		assertEquals(order2.getId(), result.get(1).getId());
		assertEquals(order3.getId(), result.get(2).getId());
		assertEquals(order1.getDate(), result.get(0).getDate());
		assertEquals(order2.getDate(), result.get(1).getDate());
		assertEquals(order3.getDate(), result.get(2).getDate());
		assertEquals(order1.getTotalPrice(), result.get(0).getTotalPrice());
		assertEquals(order2.getTotalPrice(), result.get(1).getTotalPrice());
		assertEquals(order3.getTotalPrice(), result.get(2).getTotalPrice());
		assertEquals(order1.getOrderDetails(), result.get(0).getOrderDetails());
		assertEquals(order2.getOrderDetails(), result.get(1).getOrderDetails());
		assertEquals(order3.getOrderDetails(), result.get(2).getOrderDetails());
		assertEquals(order1.getClient(), result.get(0).getClient());
		assertEquals(order2.getClient(), result.get(1).getClient());
		assertEquals(order3.getClient(), result.get(2).getClient());
		assertEquals(orders.size(), result.size());

		verify(mockOrderRepository).findAll();
	}
}