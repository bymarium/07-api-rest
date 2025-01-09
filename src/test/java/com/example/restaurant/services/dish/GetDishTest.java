package com.example.restaurant.services.dish;

import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetDishTest {
	private IDishRepository mockDishRepository;
	private GetDish getDish;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		getDish = new GetDish(mockDishRepository);
	}

	@Test
	void execute() {
		Dish dish = new Dish(1L, "Pollo apanado", "Pollo apanado con miel y papa", 38f);

		when(mockDishRepository.findById(1L)).thenReturn(Optional.of(dish));

		Dish result = getDish.execute(1L);

		assertNotNull(result);
		assertEquals(dish.getId(), result.getId());
		assertEquals(dish.getName(), result.getName());
		assertEquals(dish.getDescription(), result.getDescription());
		assertEquals(dish.getPrice(), result.getPrice());
		assertEquals(dish.getAdjust(), result.getAdjust());
		assertEquals(dish.getDishType(), result.getDishType());
		assertEquals(dish.getMenu(), result.getMenu());
		assertEquals(dish.getOrderDetails(), result.getOrderDetails());

		verify(mockDishRepository).findById(1L);
	}

	@Test
	void executeWhenDishNotFound() {
		when(mockDishRepository.findById(anyLong())).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> getDish.execute(1L)
		);

		assertEquals("Plato con id 1 no encontrado", exception.getMessage());

		verify(mockDishRepository).findById(anyLong());
	}
}