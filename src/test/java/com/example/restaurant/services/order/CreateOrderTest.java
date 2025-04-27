package com.example.restaurant.services.order;

import com.example.restaurant.constants.Type;
import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.dtos.OrderDetailDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;
import com.example.restaurant.models.OrderDetail;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.repositories.IOrderRepository;
import com.example.restaurant.services.client.UpdateTypeClient;
import com.example.restaurant.services.dish.UpdateTypeDish;
import com.example.restaurant.services.orderdetail.CreateOrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateOrderTest {
	private IOrderRepository mockOrderRepository;
	private CreateOrderDetail mockCreateOrderDetail;
	private IClientRepository mockClientRepository;
	private CreateOrder createOrder;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		mockCreateOrderDetail = mock(CreateOrderDetail.class);
		mockClientRepository = mock(IClientRepository.class);
		UpdateTypeClient updateTypeClient = mock(UpdateTypeClient.class);
		UpdateTypeDish updateTypeDish = mock(UpdateTypeDish.class);
		createOrder = new CreateOrder(
			mockOrderRepository,
			mockCreateOrderDetail,
			mockClientRepository,
			updateTypeClient,
			updateTypeDish
		);
	}

	@Test
	void execute() {
		Client client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		client.setUserType(Type.BRONZE.getName());
		Dish dish = new Dish(1L, "Pollo apanado", "Pollo con papas", 28f);

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

		List<OrderDetail> orderDetails = List.of(orderDetail1, orderDetail2);

		Order order = new Order(1L, 196f);
		order.setClient(client);
		order.setOrderDetails(orderDetails);

		when(mockClientRepository.findById(1L)).thenReturn(Optional.of(client));
		when(mockCreateOrderDetail.execute(orderDetailsDTO, 1L)).thenReturn(orderDetails);
		when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

		Order response = createOrder.execute(orderDTO);

		assertNotNull(response);
		assertEquals(order.getId(), response.getId());
		assertEquals(order.getDate(), response.getDate());
		assertEquals(order.getTotalPrice(), response.getTotalPrice());
		assertEquals(order.getClient(), response.getClient());
		assertEquals(order.getOrderDetails(), response.getOrderDetails());

		verify(mockClientRepository).findById(anyLong());
		verify(mockCreateOrderDetail).execute(anyList(), anyLong());
		verify(mockOrderRepository, times(2)).save(any(Order.class));
	}
}