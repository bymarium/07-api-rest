package com.example.restaurant.services.client;

import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllClients implements ICommand<List<Client>> {
	private final IClientRepository clientRepository;

	@Autowired
	public GetAllClients(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<Client> execute() {
		return clientRepository.findAll();
	}
}