package com.example.restaurant.services.dish;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Menu;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDish implements ICommandModification<Dish, DishDTO> {
	private final IDishRepository dishRepository;

	@Autowired
	public UpdateDish(IDishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	public Dish execute(Long dishId, DishDTO dishDTO) {
		return dishRepository.findById(dishId).map(d -> {
			d.setName(dishDTO.getName());
			d.setDescription(dishDTO.getDescription());
			d.setPrice(dishDTO.getPrice());
			Menu menu = new Menu();
			menu.setId(dishDTO.getMenuId());
			d.setMenu(menu);
			return dishRepository.save(d);
		}).orElseThrow(() -> new RuntimeException("Plato con id " + dishId + " no encontrado"));
	}
}