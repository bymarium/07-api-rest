package com.example.restaurant.services.client;

import com.example.restaurant.constants.Type;
import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateClientTest {
	private IClientRepository mockClientRepository;
	private CreateClient createClient;

	@BeforeEach
	public void setUp() {
		mockClientRepository = mock(IClientRepository.class);
		createClient = new CreateClient(mockClientRepository);
	}

	@Test
	void execute() {
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setName("Mariana");
		clientDTO.setLastName("Usuga");
		clientDTO.setEmail("mariana@gmail.com");

		Client client = new Client();
		client.setId(1L);
		client.setName("Mariana");
		client.setLastName("Usuga");
		client.setEmail("mariana@gmail.com");
		client.setAdjust(1f);
		client.setUserType(Type.COMMON.getName());
		client.setOrders(null);

		when(mockClientRepository.save(any(Client.class))).thenReturn(client);

		Client response = createClient.execute(clientDTO);

		assertNotNull(response);
		assertEquals(client.getName(), response.getName());
		assertEquals(client.getLastName(), response.getLastName());
		assertEquals(client.getEmail(), response.getEmail());
		assertEquals(client.getAdjust(), response.getAdjust());
		assertEquals(client.getUserType(), response.getUserType());
		assertEquals(client.getOrders(), response.getOrders());

		verify(mockClientRepository).save(any(Client.class));
	}
}