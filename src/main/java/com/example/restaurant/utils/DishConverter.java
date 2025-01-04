package com.example.restaurant.utils;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;

public class DishConverter {
	public static DishDTO convertEntityToDto(Dish dish) {
		DishDTO dishDTO = new DishDTO();
		dishDTO.setName(dish.getName());
		dishDTO.setDescription(dish.getDescription());
		dishDTO.setPrice(dish.getPrice());
		dishDTO.setMenuId(dish.getMenu().getId());
		return dishDTO;
	}

	public static Dish convertDtoToEntity(DishDTO dishDTO) {
		Dish dish = new Dish();
		dish.setName(dishDTO.getName());
		dish.setDescription(dishDTO.getDescription());
		dish.setPrice(dishDTO.getPrice());
		return dish;
	}
}