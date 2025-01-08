package com.example.restaurant.services.dish;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateDishTest {
	private IDishRepository mockDishRepository;
	private CreateDish createDish;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		createDish = new CreateDish(mockDishRepository);
	}

	@Test
	void execute() {
		DishDTO dishDTO = new DishDTO();
		dishDTO.setName("Pollo apanado");
		dishDTO.setDescription("Pollo apanado con miel y papa");
		dishDTO.setPrice(38f);
		dishDTO.setMenuId(1L);

		Dish dish = new Dish(1L, "Pollo apanado", "Pollo apanado con miel y papa", 38f);

		when(mockDishRepository.save(any(Dish.class))).thenReturn(dish);

		Dish response = createDish.execute(dishDTO);

		assertNotNull(response);
		assertEquals(dish.getName(), response.getName());
		assertEquals(dish.getDescription(), response.getDescription());
		assertEquals(dish.getPrice(), response.getPrice());
		assertEquals(dish.getAdjust(), response.getAdjust());
		assertEquals(dish.getDishType(), response.getDishType());
		assertEquals(dish.getMenu(), response.getMenu());

		verify(mockDishRepository).save(any(Dish.class));
	}
}