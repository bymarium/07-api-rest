package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishDTO {
	private String name;
	private String description;
	private Float price;
	private Long menuId;
}