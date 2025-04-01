package com.example.restaurant.services.menu;

import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteMenuTest {
	private IMenuRepository mockMenuRepository;
	private DeleteMenu deleteMenu;

	@BeforeEach
	public void setUp() {
		mockMenuRepository = mock(IMenuRepository.class);
		deleteMenu = new DeleteMenu(mockMenuRepository);
	}

	@Test
	void execute() {
		when(mockMenuRepository.existsById(1L)).thenReturn(true);

		deleteMenu.execute(1L);

		verify(mockMenuRepository).existsById(1L);
		verify(mockMenuRepository).deleteById(1L);
	}

	@Test
	void executeWhenNoMenuFound() {
		when(mockMenuRepository.existsById(anyLong())).thenReturn(false);

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> deleteMenu.execute(1L)
		);

		assertEquals("Menu con id 1 no encontrado", exception.getMessage());

		verify(mockMenuRepository).existsById(anyLong());
		verify(mockMenuRepository, never()).deleteById(anyLong());
	}
}