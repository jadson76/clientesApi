package br.com.javaarquiteto.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.dtos.ConsultaFotoDto;
import br.com.javaarquiteto.domain.dtos.UploadResponseDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public interface IClienteDomainService {
	
	ClienteDto create(ClienteCreateCommand command) throws ClienteException;

	ClienteDto update(ClienteUpdateCommand command) throws ClienteException;

	ClienteDto delete(UUID id) throws ClienteException;

	ClienteDto getById(UUID id) throws ClienteException;

	List<ClienteDto> getAll();

	UploadResponseDto updateFoto(UUID idCliente, byte[] conteudoFoto) throws ClienteException;

	ConsultaFotoDto obterFoto(UUID idCliente) throws ClienteException;

}
