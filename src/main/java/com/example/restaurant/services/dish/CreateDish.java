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
		Dish dish = DishConverter.convertDtoToEntity(dishDTO);
		dishRepository.save(dish);
		return dish;
	}
}