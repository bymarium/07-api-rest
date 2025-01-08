package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.services.order.CreateOrder;
import com.example.restaurant.services.order.DeleteOrder;
import com.example.restaurant.services.order.GetAllOrders;
import com.example.restaurant.services.order.GetOrder;
import com.example.restaurant.services.order.UpdateOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {
	private final WebTestClient webTestClient;
	private final CreateOrder mockCreateOrder;
	private final GetOrder mockGetOrder;
	private final GetAllOrders mockGetAllOrders;
	private final UpdateOrder mockUpdateOrder;
	private final DeleteOrder mockDeleteOrder;

	public OrderControllerTest() {
		mockCreateOrder = mock(CreateOrder.class);
		mockGetOrder = mock(GetOrder.class);
		mockGetAllOrders = mock(GetAllOrders.class);
		mockUpdateOrder = mock(UpdateOrder.class);
		mockDeleteOrder = mock(DeleteOrder.class);

		webTestClient = WebTestClient.bindToController(
			new OrderController(
				mockCreateOrder,
				mockGetOrder,
				mockGetAllOrders,
				mockUpdateOrder,
				mockDeleteOrder)
		).build();
	}

	private Order order;
	private OrderDTO orderDTO;
	private List<Order> orders;

	@BeforeEach
	void setUp() {
		order = new Order(1L, 120f);
		orderDTO = new OrderDTO();
		orderDTO.setClientId(1L);
		OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
		orderDetailDTO.setDishId(1L);
		orderDetailDTO.setQuantity(2);
		orderDTO.setOrderDetails(List.of(orderDetailDTO));
		orders = List.of(
			new Order(1L, 120f),
			new Order(2L, 220f),
			new Order(3L, 320f)
		);
	}

	@Test
	void createOrder() {
		when(mockCreateOrder.execute(any(OrderDTO.class))).thenReturn(order);

		webTestClient.post()
			.uri("/api/orders")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(orderDTO)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Pedido creado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Order responseOrder = mapper.convertValue(response.getDetails(), Order.class);
				assertEquals(order.getId(), responseOrder.getId());
				assertEquals(order.getClient(), responseOrder.getClient());
				assertEquals(order.getOrderDetails(), responseOrder.getOrderDetails());
				assertEquals(order.getDate(), responseOrder.getDate());
				assertEquals(order.getTotalPrice(), responseOrder.getTotalPrice());
			});

		verify(mockCreateOrder).execute(any(OrderDTO.class));
	}

	@Test
	void getOrder() {
		when(mockGetOrder.execute(anyLong())).thenReturn(order);

		webTestClient.get()
			.uri("/api/orders/{orderId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(Order.class)
			.value(response -> {
				assertEquals(order.getId(), response.getId());
				assertEquals(order.getDate(), response.getDate());
				assertEquals(order.getTotalPrice(), response.getTotalPrice());
			});

		verify(mockGetOrder).execute(anyLong());
	}

	@Test
	void getAllOrders() {
		when(mockGetAllOrders.execute()).thenReturn(orders);

		webTestClient.get()
			.uri("/api/orders")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Order.class)
			.hasSize(orders.size())
			.value(response -> {
				assertEquals(orders.size(), response.size());
				assertEquals(orders.get(0).getTotalPrice(), response.get(0).getTotalPrice());
				assertEquals(orders.get(1).getTotalPrice(), response.get(1).getTotalPrice());
				assertEquals(orders.get(2).getTotalPrice(), response.get(2).getTotalPrice());
			});

		verify(mockGetAllOrders).execute();
	}

	@Test
	void updateOrder() {
		when(mockUpdateOrder.execute(anyLong(), any(OrderDTO.class))).thenReturn(order);

		webTestClient.put()
			.uri("/api/orders/{orderId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(orderDTO)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Pedido actualizado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Order responseOrder = mapper.convertValue(response.getDetails(), Order.class);
				assertEquals(order.getId(), responseOrder.getId());
				assertEquals(order.getClient(), responseOrder.getClient());
				assertEquals(order.getOrderDetails(), responseOrder.getOrderDetails());
				assertEquals(order.getDate(), responseOrder.getDate());
				assertEquals(order.getTotalPrice(), responseOrder.getTotalPrice());
			});

		verify(mockUpdateOrder).execute(anyLong(), any(OrderDTO.class));
	}

	@Test
	void deleteOrder() {
		doNothing().when(mockDeleteOrder).execute(anyLong());

		webTestClient.delete()
			.uri("/api/orders/{orderId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Pedido eliminado exitosamente", response.getMessage());
			});

		verify(mockDeleteOrder).execute(anyLong());
	}
}