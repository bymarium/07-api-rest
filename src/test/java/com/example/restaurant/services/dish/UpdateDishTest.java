package com.example.restaurant.services.dish;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class UpdateDishTest {
	private IDishRepository mockDishRepository;
	private UpdateDish updateDish;

	@BeforeEach
	public void setUp() {
		mockDishRepository = mock(IDishRepository.class);
		updateDish = new UpdateDish(mockDishRepository);
	}

	@Test
	void execute() {
		Dish dish = new Dish(1L, "Pollo apanado", "Pollo apanado con miel y papa", 38f);
		dish.setMenu(new Menu(1L, "Menu de pollo", "Descripcion del menu de pollo"));

		when(mockDishRepository.findById(1L)).thenReturn(Optional.of(dish));
		when(mockDishRepository.save(any(Dish.class))).thenReturn(dish);

		DishDTO dishDTO = new DishDTO();
		dishDTO.setName("Pollo apanado");
		dishDTO.setDescription("Pollo apanado con miel y papa");
		dishDTO.setPrice(38f);
		dishDTO.setMenuId(1L);

		Dish result = updateDish.execute(1L, dishDTO);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(dishDTO.getName(), result.getName());
		assertEquals(dishDTO.getDescription(), result.getDescription());
		assertEquals(dishDTO.getPrice(), result.getPrice());
		assertEquals(dishDTO.getMenuId(), result.getMenu().getId());

		verify(mockDishRepository).findById(anyLong());
		verify(mockDishRepository).save(any(Dish.class));
	}

	@Test
	void executeWhenDishNotFound() {
		when(mockDishRepository.findById(anyLong())).thenReturn(Optional.empty());

		DishDTO dishMock = mock(DishDTO.class);
		RuntimeException exception = assertThrows(
			RuntimeException.class,
			() -> updateDish.execute(1L, dishMock)
		);

		assertEquals("Plato con id 1 no encontrado", exception.getMessage());

		verify(mockDishRepository).findById(anyLong());
		verify(mockDishRepository, never()).save(any(Dish.class));
	}
}