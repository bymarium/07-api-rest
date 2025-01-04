package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
	private Long clientId;
	private LocalDate date;
	private List<OrderDetailDTO> orderDetails;
}