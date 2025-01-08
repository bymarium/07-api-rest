package com.example.restaurant.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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