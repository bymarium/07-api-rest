package com.example.restaurant.controllers;

import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.dtos.OrderDTO;
import com.example.restaurant.models.Order;
import com.example.restaurant.services.order.CreateOrder;
import com.example.restaurant.services.order.DeleteOrder;
import com.example.restaurant.services.order.GetAllOrders;
import com.example.restaurant.services.order.GetOrder;
import com.example.restaurant.services.order.UpdateOrder;
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
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
	private final CreateOrder createOrder;
	private final GetOrder getOrder;
	private final GetAllOrders getAllOrders;
	private final UpdateOrder updateOrder;
	private final DeleteOrder deleteOrder;

	public OrderController(CreateOrder createOrder, GetOrder getOrder, GetAllOrders getAllOrders, UpdateOrder updateOrder, DeleteOrder deleteOrder) {
		this.createOrder = createOrder;
		this.getOrder = getOrder;
		this.getAllOrders = getAllOrders;
		this.updateOrder = updateOrder;
		this.deleteOrder = deleteOrder;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageDTO createOrder(@RequestBody OrderDTO orderDTO) {
		Order order = createOrder.execute(orderDTO);
		return new MessageDTO("Pedido creado exitosamente", order);
	}

	@GetMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public Order getOrder(@PathVariable Long orderId) {
		return getOrder.execute(orderId);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Order> getAllOrders() {
		return getAllOrders.execute();
	}

	@PutMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
		Order order = updateOrder.execute(orderId, orderDTO);
		return new MessageDTO("Pedido actualizado exitosamente", order);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO deleteOrder(@PathVariable Long orderId) {
		deleteOrder.execute(orderId);
		return new MessageDTO("Pedido eliminado exitosamente");
	}
}