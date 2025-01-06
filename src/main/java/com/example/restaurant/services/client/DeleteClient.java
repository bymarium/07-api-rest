package com.example.restaurant.services.client;

import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteClient implements ICommandParametrized<Void, Long> {
	private final IClientRepository clientRepository;

	@Autowired
	public DeleteClient(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Void execute(Long clientId) {
		if (!clientRepository.existsById(clientId)) {
			throw new RuntimeException("Cliente con id " + clientId + " no encontrado");
		}

		clientRepository.deleteById(clientId);
		return null;
	}
}