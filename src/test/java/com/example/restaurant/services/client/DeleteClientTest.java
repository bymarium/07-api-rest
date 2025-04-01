package com.example.restaurant.services.client;

import com.example.restaurant.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteClientTest {
	private IClientRepository mockClientRepository;
	private DeleteClient deleteClient;

	@BeforeEach
	public void setUp() {
		mockClientRepository = mock(IClientRepository.class);
		deleteClient = new DeleteClient(mockClientRepository);
	}

	@Test
	void execute() {
		when(mockClientRepository.existsById(1L)).thenReturn(true);

		deleteClient.execute(1L);

		verify(mockClientRepository).existsById(1L);
		verify(mockClientRepository).deleteById(1L);
	}

	@Test
	void executeWhenNoClientFound() {
		when(mockClientRepository.existsById(anyLong())).thenReturn(false);

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> deleteClient.execute(1L)
		);

		assertEquals("Cliente con id 1 no encontrado", exception.getMessage());

		verify(mockClientRepository).existsById(anyLong());
		verify(mockClientRepository, never()).deleteById(anyLong());
	}
}