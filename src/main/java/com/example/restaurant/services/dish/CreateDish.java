package com.example.restaurant.services.dish;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import com.example.restaurant.utils.Converters.DishConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDish implements ICommandParametrized<Dish, DishDTO> {
	private final IDishRepository dishRepository;

	@Autowired
	public CreateDish(IDishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	public Dish execute(DishDTO dishDTO) {
		if (dishRepository.existsByNameAndDescriptionAndPriceAndMenuId(
						dishDTO.getName(),
						dishDTO.getDescription(),
						dishDTO.getPrice(),
						dishDTO.getMenuId()
		)) {
			throw new IllegalArgumentException("Plato ya existe en el sistema");
		}

		Dish dish = DishConverter.convertDtoToEntity(dishDTO);
		return dishRepository.save(dish);
	}
}