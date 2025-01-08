package com.example.restaurant.controllers;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.services.dish.CreateDish;
import com.example.restaurant.services.dish.DeleteDish;
import com.example.restaurant.services.dish.GetAllDishes;
import com.example.restaurant.services.dish.GetDish;
import com.example.restaurant.services.dish.UpdateDish;
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

class DishControllerTest {
	private final WebTestClient webTestClient;
	private final CreateDish mockCreateDish;
	private final GetDish mockGetDish;
	private final GetAllDishes mockGetAllDishes;
	private final UpdateDish mockUpdateDish;
	private final DeleteDish mockDeleteDish;

	public DishControllerTest() {
		mockCreateDish = mock(CreateDish.class);
		mockGetDish = mock(GetDish.class);
		mockGetAllDishes = mock(GetAllDishes.class);
		mockUpdateDish = mock(UpdateDish.class);
		mockDeleteDish = mock(DeleteDish.class);

		webTestClient = WebTestClient.bindToController(
			new DishController(
				mockCreateDish,
				mockGetDish,
				mockGetAllDishes,
				mockUpdateDish,
				mockDeleteDish)
		).build();
	}

	private Dish dish;
	private DishDTO dishDTO;
	private List<Dish> dishes;

	@BeforeEach
	void setUp() {
		dish = new Dish(1L, "Pollo", "Pollo sabroso", 28f);
		dishDTO = new DishDTO();
		dishDTO.setName("Pollo");
		dishDTO.setDescription("Pollo sabroso");
		dishDTO.setPrice(28f);
		dishes = List.of(
			new Dish(1L, "Pollo", "Pollo sabroso", 28f),
			new Dish(2L, "Torta", "Torta sabroso", 38f),
			new Dish(3L, "Carne", "Carne sabroso", 48f)
		);
	}

	@Test
	void createDish() {
		when(mockCreateDish.execute(any(DishDTO.class))).thenReturn(dish);

		webTestClient.post()
			.uri("/api/dishes")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(dishDTO)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Plato creado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Dish responseDish = mapper.convertValue(response.getDetails(), Dish.class);
				assertEquals(dish.getId(), responseDish.getId());
				assertEquals(dish.getName(), responseDish.getName());
				assertEquals(dish.getDescription(), responseDish.getDescription());
				assertEquals(dish.getPrice(), responseDish.getPrice());
			});

		verify(mockCreateDish).execute(any(DishDTO.class));
	}

	@Test
	void getDish() {
		when(mockGetDish.execute(anyLong())).thenReturn(dish);

		webTestClient.get()
			.uri("/api/dishes/{dishId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(Dish.class)
			.value(response -> {
				assertEquals(dish.getId(), response.getId());
				assertEquals(dish.getName(), response.getName());
				assertEquals(dish.getDescription(), response.getDescription());
				assertEquals(dish.getPrice(), response.getPrice());
			});

		verify(mockGetDish).execute(anyLong());
	}

	@Test
	void getAllDishes() {
		when(mockGetAllDishes.execute()).thenReturn(dishes);

		webTestClient.get()
			.uri("/api/dishes")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBodyList(Dish.class)
			.hasSize(dishes.size())
			.value(response -> {
				assertEquals(dishes.size(), response.size());
				assertEquals(dishes.get(0).getName(), response.get(0).getName());
				assertEquals(dishes.get(1).getName(), response.get(1).getName());
				assertEquals(dishes.get(2).getName(), response.get(2).getName());
			});

		verify(mockGetAllDishes).execute();
	}

	@Test
	void updateDish() {
		when(mockUpdateDish.execute(anyLong(), any(DishDTO.class))).thenReturn(dish);

		webTestClient.put()
			.uri("/api/dishes/{dishId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(dishDTO)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Plato actualizado exitosamente", response.getMessage());
				ObjectMapper mapper = new ObjectMapper();
				Dish responseDish = mapper.convertValue(response.getDetails(), Dish.class);
				assertEquals(dish.getId(), responseDish.getId());
				assertEquals(dish.getName(), responseDish.getName());
				assertEquals(dish.getDescription(), responseDish.getDescription());
				assertEquals(dish.getPrice(), responseDish.getPrice());
			});

		verify(mockUpdateDish).execute(anyLong(), any(DishDTO.class));
	}

	@Test
	void deleteDish() {
		doNothing().when(mockDeleteDish).execute(anyLong());

		webTestClient.delete()
			.uri("/api/dishes/{dishId}", 1L)
			.exchange()
			.expectStatus().isOk()
			.expectBody(MessageDTO.class)
			.value(response -> {
				assertEquals("Plato eliminado exitosamente", response.getMessage());
			});

		verify(mockDeleteDish).execute(anyLong());
	}
}