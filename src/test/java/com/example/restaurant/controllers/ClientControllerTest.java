package com.example.restaurant.controllers;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.services.client.CreateClient;
import com.example.restaurant.services.client.DeleteClient;
import com.example.restaurant.services.client.GetAllClients;
import com.example.restaurant.services.client.GetClient;
import com.example.restaurant.services.client.UpdateClient;
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

class ClientControllerTest {
	private final WebTestClient webTestClient;
	private final CreateClient mockCreateClient;
	private final GetClient mockGetClient;
	private final GetAllClients mockGetAllClients;
	private final UpdateClient mockUpdateClient;
	private final DeleteClient mockDeleteClient;

	public ClientControllerTest() {
		mockCreateClient = mock(CreateClient.class);
		mockGetClient = mock(GetClient.class);
		mockGetAllClients = mock(GetAllClients.class);
		mockUpdateClient = mock(UpdateClient.class);
		mockDeleteClient = mock(DeleteClient.class);

		webTestClient = WebTestClient.bindToController(
			new ClientController(
				mockCreateClient,
				mockGetClient,
				mockGetAllClients,
				mockUpdateClient,
				mockDeleteClient)
		).build();
	}

	private Client client;
	private ClientDTO clientDTO;
	private List<Client> clients;

	@BeforeEach
	void setUp() {
		client = new Client(1L, "Mariana", "Usuga", "bymar@gmail.com");
		clientDTO = new ClientDTO();
		clientDTO.setName("Mariana");
		clientDTO.setLastName("Usuga");
		clientDTO.setEmail("bymar@gmail.com");
		clients = List.of(
			new Client(1L, "Mariana", "Usuga", "bymar@gmail.com"),
			new Client(2L, "Paola", "Mejia", "pao@gmail.com"),
			new Client(3L, "Jinsei", "Usuga", "jinsei@gmail.com")
		);
	}

	@Test
	void createClient() {
		when(mockCreateClient.execute(any(ClientDTO.class))).thenReturn(client);

		webTestClient.post()
			.uri("/api/clients")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(clientDTO)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Cliente creado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Client responseClient = mapper.convertValue(response.getDetails(), Client.class);
				assertEquals(client.getId(), responseClient.getId());
				assertEquals(client.getName(), responseClient.getName());
				assertEquals(client.getLastName(), responseClient.getLastName());
				assertEquals(client.getEmail(), responseClient.getEmail());
			});

		verify(mockCreateClient).execute(any(ClientDTO.class));
	}

	@Test
	void getClient() {
		when(mockGetClient.execute(anyLong())).thenReturn(client);

		webTestClient.get()
			.uri("/api/clients/{clientId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(Client.class)
			.value(response -> {
				assertEquals(client.getId(), response.getId());
				assertEquals(client.getName(), response.getName());
				assertEquals(client.getLastName(), response.getLastName());
				assertEquals(client.getEmail(), response.getEmail());
			});

		verify(mockGetClient).execute(anyLong());
	}

	@Test
	void getAllClients() {
		when(mockGetAllClients.execute()).thenReturn(clients);

		webTestClient.get()
			.uri("/api/clients")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Client.class)
			.hasSize(clients.size())
			.value(response -> {
				assertEquals(clients.size(), response.size());
				assertEquals(clients.get(0).getName(), response.get(0).getName());
				assertEquals(clients.get(1).getName(), response.get(1).getName());
				assertEquals(clients.get(2).getName(), response.get(2).getName());
			});

		verify(mockGetAllClients).execute();
	}

	@Test
	void updateClient() {
		when(mockUpdateClient.execute(anyLong(), any(ClientDTO.class))).thenReturn(client);

		webTestClient.put()
			.uri("/api/clients/{clientId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(clientDTO)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Cliente actualizado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Client responseClient = mapper.convertValue(response.getDetails(), Client.class);
				assertEquals(client.getId(), responseClient.getId());
				assertEquals(client.getName(), responseClient.getName());
				assertEquals(client.getLastName(), responseClient.getLastName());
				assertEquals(client.getEmail(), responseClient.getEmail());
			});


		verify(mockUpdateClient).execute(anyLong(), any(ClientDTO.class));
	}

	@Test
	void deleteClient() {
		doNothing().when(mockDeleteClient).execute(anyLong());

		webTestClient.delete()
			.uri("/api/clients/{clientId}", 1L)
			.exchange()
			.expectStatus().isOk();

		verify(mockDeleteClient).execute(anyLong());
	}
}