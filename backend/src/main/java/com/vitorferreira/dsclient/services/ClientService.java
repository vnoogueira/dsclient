package com.vitorferreira.dsclient.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(obj -> new ClientDTO(obj));
	}

	@Transactional(readOnly = true)
	public ClientDTO insertClient(ClientDTO obj) {
		Client entity = new Client();
		copyEntityToDto(obj, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO updateClient(Long id, ClientDTO obj) {
		try {
			Client entity = repository.getReferenceById(id);
			copyEntityToDto(obj, entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	
	public void deleteClientById(Long id) {
		try {
			repository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Database Integrity");
		} catch (DataIntegrityViolationException d) {
		}
	}

	private void copyEntityToDto(ClientDTO obj, Client entity) {
		entity.setName(obj.getName());
		entity.setCpf(obj.getCpf());
		entity.setIncome(obj.getIncome());
		entity.setBirthDate(obj.getBirthDate());
		entity.setChildren(obj.getChildren());
	}

}
