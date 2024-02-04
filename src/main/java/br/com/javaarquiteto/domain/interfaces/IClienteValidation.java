package br.com.javaarquiteto.domain.interfaces;

import java.util.UUID;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public interface IClienteValidation {
	
	void validar(ClienteCreateCommand command) throws ClienteException;

	void validar(ClienteUpdateCommand command) throws ClienteException;

	void validar(UUID idCliente) throws ClienteException;

	void validar(UUID idCliente, byte[] conteudoFoto) throws ClienteException;

}
