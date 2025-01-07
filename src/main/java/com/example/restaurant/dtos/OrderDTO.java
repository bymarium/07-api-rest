package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
	private Long clientId;
	private List<OrderDetailDTO> orderDetails;
}