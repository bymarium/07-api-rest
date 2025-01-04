package com.example.restaurant.controllers;

import com.example.restaurant.dtos.ClientDTO;
import com.example.restaurant.dtos.MessageDTO;
import com.example.restaurant.models.Client;
import com.example.restaurant.services.client.CreateClient;
import com.example.restaurant.services.client.DeleteClient;
import com.example.restaurant.services.client.GetAllClients;
import com.example.restaurant.services.client.GetClient;
import com.example.restaurant.services.client.UpdateClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
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
	public ResponseEntity<MessageDTO> createClient(@RequestBody ClientDTO clientDTO) {
		Client client = createClient.execute(clientDTO);
		return ResponseEntity.ok(new MessageDTO("Cliente creado exitosamente", client));
	}

	@GetMapping("/{clientId}")
	public ResponseEntity<Client> getClient(@PathVariable Long clientId) {
		return ResponseEntity.ok(getClient.execute(clientId));
	}

	@GetMapping
	public ResponseEntity<List<Client>> getAllClients() {
		return ResponseEntity.ok(getAllClients.execute());
	}

	@PutMapping("/{clientId}")
	public ResponseEntity<MessageDTO> updateClient(@PathVariable Long clientId, @RequestBody ClientDTO clientDTO) {
		Client client = updateClient.execute(clientId, clientDTO);
		return ResponseEntity.ok(new MessageDTO("Cliente actualizado exitosamente", client));
	}

	@DeleteMapping("/{clientId}")
	public ResponseEntity<MessageDTO> deleteClient(@PathVariable Long clientId) {
		deleteClient.execute(clientId);
		return ResponseEntity.ok(new MessageDTO("Cliente eliminado exitosamente"));
	}
}