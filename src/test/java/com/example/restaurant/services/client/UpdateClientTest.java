package com.example.restaurant.services.client;

import com.example.restaurant.constants.Type;
import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateClientTest {
	private IClientRepository mockClientRepository;
	private UpdateClient updateClient;

	@BeforeEach
	public void setUp() {
		mockClientRepository = mock(IClientRepository.class);
		updateClient = new UpdateClient(mockClientRepository);
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
		when(mockClientRepository.save(any(Client.class))).thenReturn(client);

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setName("Mariana");
		clientDTO.setLastName("Usuga");
		clientDTO.setEmail("mariana.19@gmail.com");

		Client result = updateClient.execute(1L, clientDTO);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(clientDTO.getName(), result.getName());
		assertEquals(clientDTO.getLastName(), result.getLastName());
		assertEquals(clientDTO.getEmail(), result.getEmail());

		verify(mockClientRepository).findById(anyLong());
		verify(mockClientRepository).save(client);
	}

	@Test
	void executeWhenClientNotFound() {
		when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());

		ClientDTO clientMock = mock(ClientDTO.class);
		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> updateClient.execute(1L, clientMock)
		);

		assertEquals("Cliente con id 1 no encontrado", exception.getMessage());

		verify(mockClientRepository).findById(anyLong());
		verify(mockClientRepository, never()).save(any(Client.class));
	}
}