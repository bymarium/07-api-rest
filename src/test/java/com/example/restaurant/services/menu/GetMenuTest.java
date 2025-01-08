package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
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

class GetMenuTest {
	private IMenuRepository mockMenuRepository;
	private GetMenu getMenu;

	@BeforeEach
	public void setUp() {
		mockMenuRepository = mock(IMenuRepository.class);
		getMenu = new GetMenu(mockMenuRepository);
	}

	@Test
	void execute() {
		Menu menu = new Menu();
		menu.setId(1L);
		menu.setName("Menu name");
		menu.setDescription("Menu description");
		menu.setDishes(new ArrayList<>());

		when(mockMenuRepository.findById(1L)).thenReturn(Optional.of(menu));

		Menu result = getMenu.execute(1L);

		assertNotNull(result);
		assertEquals(menu.getId(), result.getId());
		assertEquals(menu.getName(), result.getName());
		assertEquals(menu.getDescription(), result.getDescription());
		assertEquals(menu.getDishes(), result.getDishes());

		verify(mockMenuRepository).findById(anyLong());
	}

	@Test
	void executeWhenMenuNotFound() {
		when(mockMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> getMenu.execute(1L)
		);

		assertEquals("Menu con id 1 no encontrado", exception.getMessage());

		verify(mockMenuRepository).findById(anyLong());
	}
}