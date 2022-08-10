package com.vitorferreira.dsclient.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vitorferreira.dsclient.dto.ClientDTO;
import com.vitorferreira.dsclient.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		ClientDTO dtoId = service.findById(id);
		return ResponseEntity.ok().body(dtoId);
	}

	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ClientDTO> list = service.findAll(pageRequest);		
		return ResponseEntity.ok().body(list);
		
	}
	
	@PostMapping
	public ResponseEntity<ClientDTO> insertClient(@RequestBody ClientDTO obj){
		ClientDTO clients = service.insertClient(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clients.getId()).toUri();
		return ResponseEntity.created(uri).body(clients);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO obj){
		ClientDTO entity = service.updateClient(id, obj);
		return ResponseEntity.ok().body(entity);
		
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id){
		service.deleteClientById(id);	
		return ResponseEntity.noContent().build();
	}
	
	

}
