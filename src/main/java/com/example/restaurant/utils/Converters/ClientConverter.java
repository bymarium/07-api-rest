package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;

public class ClientConverter {
	public static Client convertDtoToEntity(ClientDTO clientDTO) {
		Client client = new Client();
		client.setName(clientDTO.getName());
		client.setLastName(clientDTO.getLastName());
		client.setEmail(clientDTO.getEmail());
		return client;
	}
}