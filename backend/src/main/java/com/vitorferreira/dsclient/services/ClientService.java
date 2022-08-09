package com.vitorferreira.dsclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vitorferreira.dsclient.dto.ClientDTO;
import com.vitorferreira.dsclient.entities.Client;
import com.vitorferreira.dsclient.repositories.ClientRepository;
import com.vitorferreira.dsclient.services.exception.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> client = repository.findById(id);
		Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));
		return new ClientDTO(entity);
	}

}
