package br.com.javaarquiteto.domain.interfaces;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;

public interface IEmailService {
	
	void send(ClienteCreateCommand command);

}
