package com.example.restaurant.services.menu;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
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

class UpdateMenuTest {
	private IMenuRepository mockMenuRepository;
	private UpdateMenu updateMenu;

	@BeforeEach
	public void setUp() {
		mockMenuRepository = mock(IMenuRepository.class);
		updateMenu = new UpdateMenu(mockMenuRepository);
	}

	@Test
	void execute() {
		Menu menu = new Menu();
		menu.setId(1L);
		menu.setName("Menu name");
		menu.setDescription("Menu description");
		menu.setDishes(new ArrayList<>());

		when(mockMenuRepository.findById(1L)).thenReturn(Optional.of(menu));
		when(mockMenuRepository.save(any(Menu.class))).thenReturn(menu);

		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setName("Updated menu name");
		menuDTO.setDescription("Updated menu description");

		Menu result = updateMenu.execute(1L, menuDTO);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(menuDTO.getName(), result.getName());
		assertEquals(menuDTO.getDescription(), result.getDescription());

		verify(mockMenuRepository).findById(anyLong());
		verify(mockMenuRepository).save(any(Menu.class));
	}

	@Test
	void executeWhenMenuNotFound() {
		when(mockMenuRepository.findById(anyLong())).thenReturn(Optional.empty());

		MenuDTO menuMock = mock(MenuDTO.class);
		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> updateMenu.execute(1L, menuMock)
		);

		assertEquals("Menu con id 1 no encontrado", exception.getMessage());

		verify(mockMenuRepository).findById(anyLong());
		verify(mockMenuRepository, never()).save(any(Menu.class));
	}
}