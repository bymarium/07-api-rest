package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Menu;

public class DishConverter {
	public static Dish convertDtoToEntity(DishDTO dishDTO) {
		Dish dish = new Dish();
		dish.setName(dishDTO.getName());
		dish.setDescription(dishDTO.getDescription());
		dish.setPrice(dishDTO.getPrice());
		Menu menu = new Menu();
		menu.setId(dishDTO.getMenuId());
		dish.setMenu(menu);
		return dish;
	}
}