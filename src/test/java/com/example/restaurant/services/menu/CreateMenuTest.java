package com.example.restaurant.services.menu;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateMenuTest {
	private IMenuRepository mockMenuRepository;
	private CreateMenu createMenu;

	@BeforeEach
	public void setUp() {
		mockMenuRepository = mock(IMenuRepository.class);
		createMenu = new CreateMenu(mockMenuRepository);
	}

	@Test
	void execute() {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setName("Menu name");
		menuDTO.setDescription("Menu description");

		Menu menu = new Menu();
		menu.setId(1L);
		menu.setName("Menu name");
		menu.setDescription("Menu description");
		menu.setDishes(new ArrayList<>());

		when(mockMenuRepository.save(any(Menu.class))).thenReturn(menu);

		Menu result = createMenu.execute(menuDTO);

		assertNotNull(result);
		assertEquals(menu.getId(), result.getId());
		assertEquals(menu.getName(), result.getName());
		assertEquals(menu.getDescription(), result.getDescription());
		assertEquals(menu.getDishes(), result.getDishes());

		verify(mockMenuRepository).save(any(Menu.class));
	}
}