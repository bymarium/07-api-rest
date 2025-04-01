package com.example.restaurant.controllers;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.services.dish.CreateDish;
import com.example.restaurant.services.dish.DeleteDish;
import com.example.restaurant.services.dish.GetAllDishes;
import com.example.restaurant.services.dish.GetDish;
import com.example.restaurant.services.dish.UpdateDish;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "http://localhost:4200")
public class DishController {
	private final CreateDish createDish;
	private final GetDish getDish;
	private final GetAllDishes getAllDishes;
	private final UpdateDish updateDish;
	private final DeleteDish deleteDish;

	public DishController(CreateDish createDish, GetDish getDish, GetAllDishes getAllDishes, UpdateDish updateDish, DeleteDish deleteDish) {
		this.createDish = createDish;
		this.getDish = getDish;
		this.getAllDishes = getAllDishes;
		this.updateDish = updateDish;
		this.deleteDish = deleteDish;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageDTO createDish(@RequestBody DishDTO dishDTO) {
		Dish dish = createDish.execute(dishDTO);
		return new MessageDTO("Plato creado exitosamente", dish);
	}

	@GetMapping("/{dishId}")
	@ResponseStatus(HttpStatus.OK)
	public Dish getDish(@PathVariable Long dishId) {
		return getDish.execute(dishId);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Dish> getAllDishes() {
		return getAllDishes.execute();
	}

	@PutMapping("/{dishId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO updateDish(@PathVariable Long dishId, @RequestBody DishDTO dishDTO) {
		Dish dish = updateDish.execute(dishId, dishDTO);
		return new MessageDTO("Plato actualizado exitosamente", dish);
	}

	@DeleteMapping("/{dishId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO deleteDish(@PathVariable Long dishId) {
		deleteDish.execute(dishId);
		return new MessageDTO("Plato eliminado exitosamente");
	}
}