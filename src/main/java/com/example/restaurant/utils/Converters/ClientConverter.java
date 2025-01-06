package com.example.restaurant.utils.Converters;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;

public class ClientConverter {
	public static ClientDTO convertEntityToDto(Client client) {
		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setName(client.getName());
		clientDTO.setLastName(client.getLastName());
		clientDTO.setEmail(client.getEmail());
		return clientDTO;
	}

	public static Client convertDtoToEntity(ClientDTO clientDTO) {
		Client client = new Client();
		client.setName(clientDTO.getName());
		client.setLastName(clientDTO.getLastName());
		client.setEmail(clientDTO.getEmail());
		return client;
	}
}
