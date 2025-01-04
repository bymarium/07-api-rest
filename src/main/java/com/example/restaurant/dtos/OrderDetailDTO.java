package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
	private Long dishId;
	private Integer quantity;
}