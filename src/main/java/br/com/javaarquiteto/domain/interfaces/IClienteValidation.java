package br.com.javaarquiteto.domain.interfaces;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public interface IClienteValidation {
	
	void validar(ClienteCreateCommand command) throws ClienteException;

}
