package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {
	private String message;
	private Object details;

	public MessageDTO(String message, Object details) {
		this.message = message;
		this.details = details;
	}

	public MessageDTO(String message) {
		this.message = message;
	}
}