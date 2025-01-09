package com.example.restaurant.services.order;

import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IOrderDetailRepository;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.orderdetail.UpdateOrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderTest {
	private IOrderRepository mockOrderRepository;
	private UpdateOrderDetail mockUpdateOrderDetail;
	private IOrderDetailRepository mockOrderDetailRepository;
	private UpdateOrder updateOrder;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		mockUpdateOrderDetail = mock(UpdateOrderDetail.class);
		mockOrderDetailRepository = mock(IOrderDetailRepository.class);
		updateOrder = new UpdateOrder(
			mockOrderRepository,
			mockUpdateOrderDetail,
			mockOrderDetailRepository
		);
	}

	@Test
	void execute() {
		Client client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		Dish dish = new Dish(1L, "Pollo asado", "Pollo con papas", 28f);

		OrderDetailDTO orderDetailDTO1 = new OrderDetailDTO();
		orderDetailDTO1.setQuantity(2);
		orderDetailDTO1.setDishId(dish.getId());

		OrderDetailDTO orderDetailDTO2 = new OrderDetailDTO();
		orderDetailDTO2.setQuantity(5);
		orderDetailDTO2.setDishId(dish.getId());

		List<OrderDetailDTO> orderDetailsDTO = List.of(orderDetailDTO1, orderDetailDTO2);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setClientId(client.getId());
		orderDTO.setOrderDetails(orderDetailsDTO);

		OrderDetail orderDetail1 = new OrderDetail(1L, 2, 28f);
		orderDetail1.setSubTotal(56f);

		OrderDetail orderDetail2 = new OrderDetail(2L, 5, 28f);
		orderDetail2.setSubTotal(140f);

		List<OrderDetail> orderDetails = new ArrayList<>(List.of(orderDetail1, orderDetail2));

		Order order = new Order(1L, 196f);
		order.setClient(client);
		order.setOrderDetails(orderDetails);

		when(mockOrderRepository.findById(anyLong())).thenReturn(Optional.of(order));
		mockUpdateOrderDetail.execute(orderDTO.getOrderDetails(), order.getId());
		when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

		Order response = updateOrder.execute(1L, orderDTO);

		assertNotNull(response);
		assertEquals(order.getId(), response.getId());
		assertEquals(order.getDate(), response.getDate());
		assertEquals(order.getTotalPrice(), response.getTotalPrice());
		assertEquals(order.getClient(), response.getClient());
		assertEquals(order.getOrderDetails(), response.getOrderDetails());

		verify(mockOrderRepository).findById(anyLong());
		verify(mockUpdateOrderDetail, times(2)).execute(anyList(), anyLong());
		verify(mockOrderRepository, times(2)).save(any(Order.class));
	}

	@Test
	void executeWhenDishNotFound() {
		when(mockOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

		OrderDTO orderMock = mock(OrderDTO.class);
		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> updateOrder.execute(1L, orderMock)
		);

		assertEquals("Pedido con id 1 no encontrado", exception.getMessage());

		verify(mockOrderRepository).findById(anyLong());
		verify(mockOrderRepository, never()).save(any(Order.class));
	}
}