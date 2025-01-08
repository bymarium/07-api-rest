package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllMenuTest {
	private IMenuRepository mockMenuRepository;
	private GetAllMenu getAllMenu;

	@BeforeEach
	public void setUp() {
		mockMenuRepository = mock(IMenuRepository.class);
		getAllMenu = new GetAllMenu(mockMenuRepository);
	}

	@Test
	void execute() {
		Menu menu1 = new Menu(1L, "menu 1", "menu description 1");
		Menu menu2 = new Menu(2L, "menu 2", "menu description 2");
		Menu menu3 = new Menu(3L, "menu 3", "menu description 3");

		List<Menu> menus = List.of(menu1, menu2, menu3);

		when(mockMenuRepository.findAll()).thenReturn(menus);

		List<Menu> result = getAllMenu.execute();

		assertEquals(menu1.getId(), result.get(0).getId());
		assertEquals(menu2.getId(), result.get(1).getId());
		assertEquals(menu3.getId(), result.get(2).getId());
		assertEquals(menu1.getName(), result.get(0).getName());
		assertEquals(menu2.getName(), result.get(1).getName());
		assertEquals(menu3.getName(), result.get(2).getName());
		assertEquals(menu1.getDescription(), result.get(0).getDescription());
		assertEquals(menu2.getDescription(), result.get(1).getDescription());
		assertEquals(menu3.getDescription(), result.get(2).getDescription());
		assertEquals(menus.size(), result.size());

		verify(mockMenuRepository).findAll();
	}
}