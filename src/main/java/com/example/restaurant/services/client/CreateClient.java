package com.example.restaurant.services.client;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.repositories.IClientRepository;
import com.example.restaurant.services.interfaces.ICommandParametrized;
import com.example.restaurant.utils.Converters.ClientConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateClient implements ICommandParametrized<Client, ClientDTO> {
	private final IClientRepository clientRepository;

	@Autowired
	public CreateClient(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Client execute(ClientDTO clientDTO) {
		Client client = ClientConverter.convertDtoToEntity(clientDTO);
		clientRepository.save(client);
		return client;
	}
}