package com.example.restaurant.services.client;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandWithParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateClient implements ICommandWithParameters<Client, Long, ClientDTO> {
	private final IClientRepository clientRepository;

	@Autowired
	public UpdateClient(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Client execute(Long clientId, ClientDTO clientDTO) {
		return clientRepository.findById(clientId).map(c -> {
			c.setName(clientDTO.getName());
			c.setLastName(clientDTO.getLastName());
			c.setEmail(clientDTO.getEmail());
			return clientRepository.save(c);
		}).orElseThrow(() -> new RuntimeException("Cliente con id " + clientId + " no encontrado"));
	}
}