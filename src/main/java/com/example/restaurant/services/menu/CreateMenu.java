package com.example.restaurant.services.menu;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import com.example.restaurant.utils.Converters.MenuConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMenu implements ICommandParametrized<Menu, MenuDTO> {
	private final IMenuRepository menuRepository;

	@Autowired
	public CreateMenu(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public Menu execute(MenuDTO menuDTO) {
		Menu menu = MenuConverter.convertDtoToEntity(menuDTO);
		menuRepository.save(menu);
		return menu;
	}
}