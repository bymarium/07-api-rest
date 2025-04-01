package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllDishesTest {
	private IDishRepository mockDishRepository;
	private GetAllDishes getAllDishes;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		getAllDishes = new GetAllDishes(mockDishRepository);
	}

	@Test
	void execute() {
		Dish dish1 = new Dish(1L, "Pollo", "Pollo sabroso", 28f);
		Dish dish2 = new Dish(2L, "Torta", "Torta sabroso", 38f);
		Dish dish3 = new Dish(3L, "Carne", "Carne sabroso", 48f);
		List<Dish> dishes = List.of(dish1, dish2, dish3);

		when(mockDishRepository.findAll()).thenReturn(dishes);

		List<Dish> result = getAllDishes.execute();

		assertEquals(dish1.getId(), result.get(0).getId());
		assertEquals(dish2.getId(), result.get(1).getId());
		assertEquals(dish3.getId(), result.get(2).getId());
		assertEquals(dish1.getName(), result.get(0).getName());
		assertEquals(dish2.getName(), result.get(1).getName());
		assertEquals(dish3.getName(), result.get(2).getName());
		assertEquals(dish1.getDescription(), result.get(0).getDescription());
		assertEquals(dish2.getDescription(), result.get(1).getDescription());
		assertEquals(dish3.getDescription(), result.get(2).getDescription());
		assertEquals(dish1.getPrice(), result.get(0).getPrice());
		assertEquals(dish2.getPrice(), result.get(1).getPrice());
		assertEquals(dish3.getPrice(), result.get(2).getPrice());
		assertEquals(dishes.size(), result.size());

		verify(mockDishRepository).findAll();
	}
}