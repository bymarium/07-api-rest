package com.example.restaurant.services.dish;

import com.example.restaurant.repositories.IDishRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDish implements ICommandParametrized<Void, Long> {
	private final IDishRepository dishRepository;

	@Autowired
	public DeleteDish(IDishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	public Void execute(Long dishId) {
		if (!dishRepository.existsById(dishId)) {
			throw new RuntimeException("Plato con id " + dishId + " no encontrado");
		}

		dishRepository.deleteById(dishId);
		return null;
	}
}