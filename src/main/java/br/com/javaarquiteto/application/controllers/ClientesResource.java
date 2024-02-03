package br.com.javaarquiteto.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteDomainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "API de Clientes")
@RestController
@RequestMapping(value = "/api/clientes")
@CrossOrigin(origins = "http://localhost:8080")
public class ClientesResource {
	
	@Autowired
	private IClienteDomainService service;
	
	@Operation(summary = "Inclui registro de cliente")
	@PostMapping
	public ClienteDto post(@RequestBody @Valid ClienteCreateCommand command) throws ClienteException {
		return service.create(command);

	}

}
