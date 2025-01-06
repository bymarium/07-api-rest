package com.example.restaurant.models;

import com.example.restaurant.constants.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "dishes")
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Float price;
	private String dishType = Type.COMMON.getName();
	private Float adjust = 1f;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	@JsonIgnore
	private Menu menu;

	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderDetail> orderDetails;

	public Dish(Long id, String name, String description, Float price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Dish() {
	}

	public Float applyAdjust() {
		return dishType.equals(Type.POPULAR.getName()) ? price * adjust : price;
	}
}