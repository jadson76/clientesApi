package br.com.javaarquiteto.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.dtos.ClienteLogResponseDto;
import br.com.javaarquiteto.domain.dtos.ConsultaFotoDto;
import br.com.javaarquiteto.domain.dtos.UploadResponseDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteDomainService;
import br.com.javaarquiteto.domain.interfaces.IClienteLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@Tag(name = "API de Clientes")
@RestController
@RequestMapping(value = "/api/clientes")
@CrossOrigin(origins = "http://localhost:8080")
public class ClientesResource {
	
	@Autowired
	private IClienteDomainService service;
	@Autowired
	private IClienteLogService logService;
	
	@Operation(summary = "Inclui registro de cliente")
	@PostMapping
	public ResponseEntity<ClienteDto> post(@RequestBody @Valid ClienteCreateCommand command) throws ClienteException {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(command)); 

	}
	
	@Operation(summary = "Altera registro de cliente")
	@PutMapping
	public ResponseEntity<ClienteDto> put(@RequestBody @Valid ClienteUpdateCommand command) throws ClienteException {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.update(command)); 		

	}
	
	@Operation(summary = "Remove registro de cliente")	
	@DeleteMapping("/{id}")
	public ClienteDto delete( @PathVariable("id") @NotNull(message = "{id.notnull}")  UUID id) throws ClienteException {
		return service.delete(id);

	}
	
	@Operation(summary = "Consulta todos os registros de clientes")
	@GetMapping
	public ResponseEntity<List<ClienteDto>> getAll() throws ClienteException {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll()); 	

	}
	
	@Operation(summary = "Consulta registro de cliente pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> getCliente( @PathVariable(name="id") @NotNull(message = "{id.notnull}") UUID id) throws ClienteException {
		return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));	

	}
	
	@Operation(summary = "Altera imagem foto do cliente")
	@PutMapping("/foto/{id}")
	public ResponseEntity<UploadResponseDto> updateFoto(@PathVariable("id") @NotNull(message = "{id.notnull}") UUID idCliente , @RequestBody byte[] conteudoFoto) throws ClienteException {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.updateFoto(idCliente,conteudoFoto));

	}
	
	@Operation(summary = "Consulta imagem foto do cliente")
	@GetMapping("/foto/{id}")
	public ResponseEntity<ConsultaFotoDto> getFoto(@PathVariable("id") @NotNull(message = "{id.notnull}") UUID idCliente ) throws ClienteException {
		
		return ResponseEntity.status(HttpStatus.OK).body(service.obterFoto(idCliente));
	}
	
	@Operation(summary = "Consulta Log de cliente")
	@GetMapping("/log/{id}")
	public ResponseEntity<ClienteLogResponseDto> getLog(@PathVariable("id") @NotNull(message = "{id.notnull}") UUID idCliente ) throws ClienteException {
	
		return ResponseEntity.status(HttpStatus.OK).body(logService.obterLog(idCliente));

	}

}
