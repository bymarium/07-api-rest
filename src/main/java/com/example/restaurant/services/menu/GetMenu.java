package com.example.restaurant.services.menu;

import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetMenu implements ICommandParametrized<Menu, Long> {
	private final IMenuRepository menuRepository;

	@Autowired
	public GetMenu(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public Menu execute(Long menuId) {
		return menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu con id " + menuId + " no encontrado"));
	}
}