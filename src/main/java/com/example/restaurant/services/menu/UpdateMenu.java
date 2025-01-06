package com.example.restaurant.services.menu;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateMenu implements ICommandModification<Menu, MenuDTO> {
	private final IMenuRepository menuRepository;

	@Autowired
	public UpdateMenu(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public Menu execute(Long menuId, MenuDTO menuDTO) {
		return menuRepository.findById(menuId).map(m -> {
			m.setName(menuDTO.getName());
			m.setDescription(menuDTO.getDescription());
			return menuRepository.save(m);
		}).orElseThrow(() -> new RuntimeException("Menu con id " + menuId + " no encontrado"));
	}
}