package com.example.restaurant.models;

import com.example.restaurant.constants.Type;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Float price;
	private String dishType;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;

	public Dish(Long id, String name, String description, Float price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.dishType = Type.COMMON.getName();
	}

	public Dish() {
	}
}