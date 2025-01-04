package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {
	private String name;
	private String description;
	private Float price;
	private Long menuId;
}