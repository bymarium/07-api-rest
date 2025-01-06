package com.example.restaurant.controllers;

import com.example.restaurant.dtos.DishDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Dish;
import com.example.restaurant.services.dish.CreateDish;
import com.example.restaurant.services.dish.DeleteDish;
import com.example.restaurant.services.dish.GetAllDishes;
import com.example.restaurant.services.dish.GetDish;
import com.example.restaurant.services.dish.UpdateDish;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
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
	public ResponseEntity<MessageDTO> createDish(@RequestBody DishDTO dishDTO) {
		Dish dish = createDish.execute(dishDTO);
		return ResponseEntity.ok(new MessageDTO("Plato creado exitosamente", dish));
	}

	@GetMapping("/{dishId}")
	public ResponseEntity<Dish> getDish(@PathVariable Long dishId) {
		return ResponseEntity.ok(getDish.execute(dishId));
	}

	@GetMapping
	public ResponseEntity<List<Dish>> getAllDishes() {
		return ResponseEntity.ok(getAllDishes.execute());
	}

	@PutMapping("/{dishId}")
	public ResponseEntity<MessageDTO> updateDish(@PathVariable Long dishId, @RequestBody DishDTO dishDTO) {
		Dish dish = updateDish.execute(dishId, dishDTO);
		return ResponseEntity.ok(new MessageDTO("Plato actualizado exitosamente", dish));
	}

	@DeleteMapping("/{dishId}")
	public ResponseEntity<MessageDTO> deleteDish(@PathVariable Long dishId) {
		deleteDish.execute(dishId);
		return ResponseEntity.ok(new MessageDTO("Plato eliminado exitosamente"));
	}
}