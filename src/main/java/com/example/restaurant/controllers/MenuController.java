package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MenuDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Menu;
import com.example.restaurant.services.menu.CreateMenu;
import com.example.restaurant.services.menu.DeleteMenu;
import com.example.restaurant.services.menu.GetAllMenu;
import com.example.restaurant.services.menu.GetMenu;
import com.example.restaurant.services.menu.UpdateMenu;
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
@RequestMapping("/api/menus")
public class MenuController {
	private final CreateMenu createMenu;
	private final GetMenu getMenu;
	private final GetAllMenu getAllMenu;
	private final UpdateMenu updateMenu;
	private final DeleteMenu deleteMenu;

	public MenuController(CreateMenu createMenu, GetMenu getMenu, GetAllMenu getAllMenu, UpdateMenu updateMenu, DeleteMenu deleteMenu) {
		this.createMenu = createMenu;
		this.getMenu = getMenu;
		this.getAllMenu = getAllMenu;
		this.updateMenu = updateMenu;
		this.deleteMenu = deleteMenu;
	}

	@PostMapping
	public ResponseEntity<MessageDTO> createMenu(@RequestBody MenuDTO menuDTO) {
		Menu menu = createMenu.execute(menuDTO);
		return ResponseEntity.ok(new MessageDTO("Menu creado exitosamente", menu));
	}

	@GetMapping("/{menuId}")
	public ResponseEntity<Menu> getMenu(@PathVariable Long menuId) {
		return ResponseEntity.ok(getMenu.execute(menuId));
	}

	@GetMapping
	public ResponseEntity<List<Menu>> getAllMenu() {
		return ResponseEntity.ok(getAllMenu.execute());
	}

	@PutMapping("/{menuId}")
	public ResponseEntity<MessageDTO> updateMenu(@PathVariable Long menuId, @RequestBody MenuDTO menuDTO) {
		Menu menu = updateMenu.execute(menuId, menuDTO);
		return ResponseEntity.ok(new MessageDTO("Menu actualizado exitosamente", menu));
	}

	@DeleteMapping("/{menuId}")
	public ResponseEntity<MessageDTO> deleteMenu(@PathVariable Long menuId) {
		deleteMenu.execute(menuId);
		return ResponseEntity.ok(new MessageDTO("Menu eliminado exitosamente"));
	}
}