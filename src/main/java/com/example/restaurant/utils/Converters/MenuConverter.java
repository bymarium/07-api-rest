package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;

public class MenuConverter {
	public static Menu convertDtoToEntity(MenuDTO menuDto) {
		Menu menu = new Menu();
		menu.setName(menuDto.getName());
		menu.setDescription(menuDto.getDescription());
		return menu;
	}
}