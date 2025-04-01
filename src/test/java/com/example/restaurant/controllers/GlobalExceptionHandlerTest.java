package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.services.client.CreateClient;
import com.example.restaurant.services.client.DeleteClient;
import com.example.restaurant.services.client.GetAllClients;
import com.example.restaurant.services.client.GetClient;
import com.example.restaurant.services.client.UpdateClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {
	private final WebTestClient webTestClient;
	private final GetClient mockGetClient;

	public GlobalExceptionHandlerTest() {
		CreateClient mockCreateClient = mock(CreateClient.class);
		mockGetClient = mock(GetClient.class);
		GetAllClients mockGetAllClients = mock(GetAllClients.class);
		UpdateClient mockUpdateClient = mock(UpdateClient.class);
		DeleteClient mockDeleteClient = mock(DeleteClient.class);

		webTestClient = WebTestClient.bindToController(
				new ClientController(
					mockCreateClient,
					mockGetClient,
					mockGetAllClients,
					mockUpdateClient,
					mockDeleteClient)
			).controllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	void handleRuntimeException() {
		when(mockGetClient.execute(5L)).thenThrow(new RuntimeException("Cliente con id 5 no encontrado"));

		webTestClient.get().
			uri("/api/clients/{clientId}", 5L)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Cliente con id 5 no encontrado", response.getMessage());
			});

		verify(mockGetClient).execute(5L);
	}
}