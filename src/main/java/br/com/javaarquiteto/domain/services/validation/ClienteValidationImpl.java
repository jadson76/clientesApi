package br.com.javaarquiteto.domain.services.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.entities.Cliente;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteValidation;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IClienteRepository;

@Component
public class ClienteValidationImpl implements IClienteValidation{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteValidationImpl.class);
	
	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	public void validar(ClienteCreateCommand command) throws ClienteException {
		Cliente cliente = clienteRepository.find(command.getCpf());
		
		if(cliente != null) {
			LOGGER.error("Já existe um cliente com este CPF.");
			throw new ClienteException("Já existe um cliente com este CPF.");
		}
			
		
		if(command.getFoto() != null) {
			 if (command.getFoto().length > 2 * 1024 * 1024) { // 2 MB em bytes
				 LOGGER.error("Tamanho da imagem da foto excede 2 MB.");
				 throw new ClienteException("Tamanho da imagem da foto excede 2 MB.");
		        }
		}
		
		
	}

}
