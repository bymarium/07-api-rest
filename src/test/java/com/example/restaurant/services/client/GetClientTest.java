package com.example.restaurant.services.client;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetClientTest {
	private IClientRepository mockClientRepository;
	private GetClient getClient;

	@BeforeEach
	public void setUp() {
		mockClientRepository = mock(IClientRepository.class);
		getClient = new GetClient(mockClientRepository);
	}

	@Test
	void execute() {
		Client client = new Client();
		client.setId(1L);
		client.setName("Mariana");
		client.setLastName("Usuga");
		client.setEmail("mariana@gmail.com");
		client.setAdjust(1f);
		client.setUserType(Type.COMMON.getName());
		client.setOrders(new ArrayList<>());

		when(mockClientRepository.findById(1L)).thenReturn(Optional.of(client));

		Client result = getClient.execute(1L);

		assertNotNull(result);
		assertEquals(client.getId(), result.getId());
		assertEquals(client.getName(), result.getName());
		assertEquals(client.getLastName(), result.getLastName());
		assertEquals(client.getEmail(), result.getEmail());
		assertEquals(client.getAdjust(), result.getAdjust());
		assertEquals(client.getUserType(), result.getUserType());
		assertEquals(client.getOrders(), result.getOrders());

		verify(mockClientRepository).findById(1L);
	}

	@Test
	void executeWhenClientNotFound() {
		when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> getClient.execute(1L)
		);

		assertEquals("Cliente con id 1 no encontrado", exception.getMessage());

		verify(mockClientRepository).findById(anyLong());
	}
}