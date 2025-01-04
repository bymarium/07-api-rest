package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetClient implements ICommandParametrized<Client, Long> {
	private final IClientRepository clientRepository;

	@Autowired
	public GetClient(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Client execute(Long clientId) {
		return clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Cliente con id " + clientId + " no encontrado"));
	}
}