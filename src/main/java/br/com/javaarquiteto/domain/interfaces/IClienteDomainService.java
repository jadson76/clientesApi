package br.com.javaarquiteto.domain.interfaces;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public interface IClienteDomainService {
	
	ClienteDto create(ClienteCreateCommand command) throws ClienteException;

}
