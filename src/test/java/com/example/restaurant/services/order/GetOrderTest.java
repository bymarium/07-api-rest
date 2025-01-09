package com.example.restaurant.services.order;

import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetOrderTest {
	private IOrderRepository mockOrderRepository;
	private GetOrder getOrder;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		getOrder = new GetOrder(mockOrderRepository);
	}

	@Test
	void execute() {
		Client client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		List<OrderDetail> orderDetails = List.of(
			new OrderDetail(1L, 2, 38f),
			new OrderDetail(3L, 5, 48f)
		);
		Order order = new Order(1L, 120f);
		order.setClient(client);
		order.setOrderDetails(orderDetails);

		when(mockOrderRepository.findById(anyLong())).thenReturn(Optional.of(order));

		Order result = getOrder.execute(1L);

		assertNotNull(result);
		assertEquals(order.getId(), result.getId());
		assertEquals(order.getDate(), result.getDate());
		assertEquals(order.getTotalPrice(), result.getTotalPrice());
		assertEquals(order.getClient(), result.getClient());
		assertEquals(order.getOrderDetails(), result.getOrderDetails());

		verify(mockOrderRepository).findById(anyLong());
	}

	@Test
	void executeWhenOrderNotFound() {
		when(mockOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> getOrder.execute(1L)
		);

		assertEquals("Pedido con id 1 no encontrado", exception.getMessage());

		verify(mockOrderRepository).findById(anyLong());
	}
}