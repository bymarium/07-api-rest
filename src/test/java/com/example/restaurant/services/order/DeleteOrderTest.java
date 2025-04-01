package com.example.restaurant.services.order;

import com.example.restaurant.repositories.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteOrderTest {
	private IOrderRepository mockOrderRepository;
	private DeleteOrder deleteOrder;

	@BeforeEach
	public void setUp() {
		mockOrderRepository = mock(IOrderRepository.class);
		deleteOrder = new DeleteOrder(mockOrderRepository);
	}

	@Test
	void execute() {
		when(mockOrderRepository.existsById(1L)).thenReturn(true);

		deleteOrder.execute(1L);

		verify(mockOrderRepository).existsById(1L);
		verify(mockOrderRepository).deleteById(1L);
	}

	@Test
	void executeWhenNoOrderFound() {
		when(mockOrderRepository.existsById(anyLong())).thenReturn(false);

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> deleteOrder.execute(1L)
		);

		assertEquals("Pedido con id 1 no encontrado", exception.getMessage());

		verify(mockOrderRepository).existsById(anyLong());
		verify(mockOrderRepository, never()).deleteById(anyLong());
	}
}