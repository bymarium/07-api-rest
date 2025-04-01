package com.example.restaurant.services.dish;

import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteDishTest {
	private IDishRepository mockDishRepository;
	private DeleteDish deleteDish;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		deleteDish = new DeleteDish(mockDishRepository);
	}

	@Test
	void execute() {
		when(mockDishRepository.existsById(1L)).thenReturn(true);

		deleteDish.execute(1L);

		verify(mockDishRepository).existsById(1L);
		verify(mockDishRepository).deleteById(1L);
	}

	@Test
	void executeWhenNoDishFound() {
		when(mockDishRepository.existsById(anyLong())).thenReturn(false);

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> deleteDish.execute(1L)
		);

		assertEquals("Plato con id 1 no encontrado", exception.getMessage());

		verify(mockDishRepository).existsById(anyLong());
		verify(mockDishRepository, never()).deleteById(anyLong());
	}
}