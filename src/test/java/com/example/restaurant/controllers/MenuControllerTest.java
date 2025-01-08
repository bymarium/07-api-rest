package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.services.menu.CreateMenu;
import com.example.restaurant.services.menu.DeleteMenu;
import com.example.restaurant.services.menu.GetAllMenu;
import com.example.restaurant.services.menu.GetMenu;
import com.example.restaurant.services.menu.UpdateMenu;
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

class MenuControllerTest {
	private final WebTestClient webTestClient;
	private final CreateMenu mockCreateMenu;
	private final GetMenu mockGetMenu;
	private final GetAllMenu mockGetAllMenus;
	private final UpdateMenu mockUpdateMenu;
	private final DeleteMenu mockDeleteMenu;

	public MenuControllerTest() {
		mockCreateMenu = mock(CreateMenu.class);
		mockGetMenu = mock(GetMenu.class);
		mockGetAllMenus = mock(GetAllMenu.class);
		mockUpdateMenu = mock(UpdateMenu.class);
		mockDeleteMenu = mock(DeleteMenu.class);

		webTestClient = WebTestClient.bindToController(
			new MenuController(
				mockCreateMenu,
				mockGetMenu,
				mockGetAllMenus,
				mockUpdateMenu,
				mockDeleteMenu)
		).build();
	}

	private Menu menu;
	private MenuDTO menuDTO;
	private List<Menu> menus;

	@BeforeEach
	void setUp() {
		menu = new Menu(1L, "Menu de pollo", "Descripcion del menu de pollo");
		menuDTO = new MenuDTO();
		menuDTO.setName("Menu de pollo");
		menuDTO.setDescription("Descripcion del menu de pollo");
		menus = List.of(
			new Menu(1L, "Menu de pollo", "Descripcion del menu de pollo"),
			new Menu(2L, "Menu de postres", "Descripcion del menu de postres"),
			new Menu(3L, "Menu de carne", "Descripcion del menu de carne")
		);
	}

	@Test
	void createMenu() {
		when(mockCreateMenu.execute(any(MenuDTO.class))).thenReturn(menu);

		webTestClient.post()
			.uri("/api/menus")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(menuDTO)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Menu creado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Menu responseClient = mapper.convertValue(response.getDetails(), Menu.class);
				assertEquals(menu.getId(), responseClient.getId());
				assertEquals(menu.getName(), responseClient.getName());
				assertEquals(menu.getDescription(), responseClient.getDescription());
			});

		verify(mockCreateMenu).execute(any(MenuDTO.class));
	}

	@Test
	void getMenu() {
		when(mockGetMenu.execute(anyLong())).thenReturn(menu);

		webTestClient.get()
			.uri("/api/menus/{menuId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(Menu.class)
			.value(response -> {
				assertEquals(menu.getId(), response.getId());
				assertEquals(menu.getName(), response.getName());
				assertEquals(menu.getDescription(), response.getDescription());
			});

		verify(mockGetMenu).execute(anyLong());
	}

	@Test
	void getAllMenu() {
		when(mockGetAllMenus.execute()).thenReturn(menus);

		webTestClient.get()
			.uri("/api/menus")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Menu.class)
			.hasSize(menus.size())
			.value(response -> {
				assertEquals(menus.size(), response.size());
				assertEquals(menus.get(0).getName(), response.get(0).getName());
				assertEquals(menus.get(1).getName(), response.get(1).getName());
				assertEquals(menus.get(2).getName(), response.get(2).getName());
			});

		verify(mockGetAllMenus).execute();
	}

	@Test
	void updateMenu() {
		when(mockUpdateMenu.execute(anyLong(), any(MenuDTO.class))).thenReturn(menu);

		webTestClient.put()
			.uri("/api/menus/{menuId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(menuDTO)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Menu actualizado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Menu responseClient = mapper.convertValue(response.getDetails(), Menu.class);
				assertEquals(menu.getId(), responseClient.getId());
				assertEquals(menu.getName(), responseClient.getName());
				assertEquals(menu.getDescription(), responseClient.getDescription());
			});

		verify(mockUpdateMenu).execute(anyLong(), any(MenuDTO.class));
	}

	@Test
	void deleteMenu() {
		doNothing().when(mockDeleteMenu).execute(anyLong());

		webTestClient.delete()
			.uri("/api/menus/{menuId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Menu eliminado exitosamente", response.getMessage());
			});

		verify(mockDeleteMenu).execute(anyLong());
	}
}