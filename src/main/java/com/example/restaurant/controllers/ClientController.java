package com.example.restaurant.controllers;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.services.client.CreateClient;
import com.example.restaurant.services.client.DeleteClient;
import com.example.restaurant.services.client.GetAllClients;
import com.example.restaurant.services.client.GetClient;
import com.example.restaurant.services.client.UpdateClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
	private final CreateClient createClient;
	private final GetClient getClient;
	private final GetAllClients getAllClients;
	private final UpdateClient updateClient;
	private final DeleteClient deleteClient;

	public ClientController(CreateClient createClient, GetClient getClient, GetAllClients getAllClients, UpdateClient updateClient, DeleteClient deleteClient) {
		this.createClient = createClient;
		this.getClient = getClient;
		this.getAllClients = getAllClients;
		this.updateClient = updateClient;
		this.deleteClient = deleteClient;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageDTO createClient(@RequestBody ClientDTO clientDTO) {
		Client client = createClient.execute(clientDTO);
		return new MessageDTO("Cliente creado exitosamente", client);
	}

	@GetMapping("/{clientId}")
	@ResponseStatus(HttpStatus.OK)
	public Client getClient(@PathVariable Long clientId) {
		return getClient.execute(clientId);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Client> getAllClients() {
		return getAllClients.execute();
	}

	@PutMapping("/{clientId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO updateClient(@PathVariable Long clientId, @RequestBody ClientDTO clientDTO) {
		Client client = updateClient.execute(clientId, clientDTO);
		return new MessageDTO("Cliente actualizado exitosamente", client);
	}

	@DeleteMapping("/{clientId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO deleteClient(@PathVariable Long clientId) {
		deleteClient.execute(clientId);
		return new MessageDTO("Cliente eliminado exitosamente");
	}
}