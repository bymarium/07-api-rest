package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllClientsTest {
	private IClientRepository mockClientRepository;
	private GetAllClients getAllClients;

	@BeforeEach
	public void setUp() {
		mockClientRepository = mock(IClientRepository.class);
		getAllClients = new GetAllClients(mockClientRepository);
	}

	@Test
	void execute() {
		Client client1 = new Client(1L, "name 1", "lastName 1", "email1@gmail.com");
		Client client2 = new Client(2L, "name 2", "lastName 2", "email2@gmail.com");
		Client client3 = new Client(3L, "name 3", "lastName 3", "email3@gmail.com");

		List<Client> clients = List.of(client1, client2, client3);

		when(mockClientRepository.findAll()).thenReturn(clients);

		List<Client> result = getAllClients.execute();

		assertEquals(client1.getId(), result.get(0).getId());
		assertEquals(client2.getId(), result.get(1).getId());
		assertEquals(client3.getId(), result.get(2).getId());
		assertEquals(client1.getName(), result.get(0).getName());
		assertEquals(client2.getName(), result.get(1).getName());
		assertEquals(client3.getName(), result.get(2).getName());
		assertEquals(client1.getLastName(), result.get(0).getLastName());
		assertEquals(client2.getLastName(), result.get(1).getLastName());
		assertEquals(client3.getLastName(), result.get(2).getLastName());
		assertEquals(client1.getEmail(), result.get(0).getEmail());
		assertEquals(client2.getEmail(), result.get(1).getEmail());
		assertEquals(client3.getEmail(), result.get(2).getEmail());
		assertEquals(clients.size(), result.size());

		verify(mockClientRepository).findAll();
	}
}