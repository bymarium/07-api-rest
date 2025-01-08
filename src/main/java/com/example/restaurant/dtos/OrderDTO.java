package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
	private Long clientId;
	private List<OrderDetailDTO> orderDetails;
}