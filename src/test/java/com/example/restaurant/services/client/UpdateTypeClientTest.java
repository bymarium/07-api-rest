package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.models.Order;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateTypeClientTest {
	private IOrderRepository mockOrderRepository;
	private IClientRepository mockClientRepository;
	private UpdateTypeClient updateTypeClient;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		mockClientRepository = mock(IClientRepository.class);
		updateTypeClient = new UpdateTypeClient(mockOrderRepository, mockClientRepository);
	}

	@Test
	void update() {
		Order order = new Order(1L, 104f);
		order.setClient(new Client(1L, "name", "lastName", "email"));

		when(mockOrderRepository.countByClient_Id(order.getClient().getId())).thenReturn(11L);

		updateTypeClient.update(order);

		verify(mockOrderRepository).countByClient_Id(1L);
		verify(mockClientRepository).save(order.getClient());
	}

	@Test
	void updateWhenNoCount() {
		Order order = new Order(1L, 104f);
		order.setClient(new Client(1L, "name", "lastName", "email"));

		when(mockOrderRepository.countByClient_Id(order.getClient().getId())).thenReturn(7L);

		updateTypeClient.update(order);

		verify(mockOrderRepository).countByClient_Id(1L);
		verify(mockClientRepository, never()).save(any(Client.class));
	}
}