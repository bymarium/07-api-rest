package com.example.restaurant.services.menu;

import com.example.restaurant.repositories.IMenuRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMenu implements ICommandParametrized<Void, Long> {
	private final IMenuRepository menuRepository;

	@Autowired
	public DeleteMenu(IMenuRepository menuRepository) {
		this.menuRepository = menuRepository;
	}

	@Override
	public Void execute(Long menuId) {
		if (!menuRepository.existsById(menuId)) {
			throw new RuntimeException("Menu con id " + menuId + " no encontrado");
		}

		menuRepository.deleteById(menuId);
		return null;
	}
}