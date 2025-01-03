package com.example.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer quantity;
	private Float unitPrice;
	private Float subTotal;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	public OrderDetail(Long id, Integer quantity, Float unitPrice, Float subTotal) {
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.subTotal = subTotal;
	}

	public OrderDetail() {
	}
}