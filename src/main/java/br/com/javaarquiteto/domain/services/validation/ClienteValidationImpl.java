package br.com.javaarquiteto.domain.services.validation;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteValidation;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IClienteRepository;

@Component
public class ClienteValidationImpl implements IClienteValidation{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteValidationImpl.class);
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	 @Value("${cpf.existe}")
	 private String cpfExiste;
	 
	 @Value("${foto.size}")
	 private String fotoSize;
	 
	 @Value("${id.nao.existe}")
	 private String idNaoExiste;

	@Override
	public void validar(ClienteCreateCommand command) throws ClienteException {
		Cliente cliente = clienteRepository.find(command.getCpf());
		
		if(cliente != null) {
			LOGGER.error(cpfExiste);
			throw new ClienteException(cpfExiste);
		}
			
		
		if(command.getFoto() != null) {
			 if (imagemTamanhoPermitido(command.getFoto())) { 
				 LOGGER.error(fotoSize);
				 throw new ClienteException(fotoSize);
		        }
		}
		
		
	}

	@Override
	public void validar(ClienteUpdateCommand command) throws ClienteException {
		
		if(!existeCliente(command.getId())) {
			LOGGER.error(idNaoExiste);
			throw new ClienteException(idNaoExiste);
		}
		
	}

	@Override
	public void validar(UUID idCliente) throws ClienteException {
		if(!existeCliente(idCliente)) {
			LOGGER.error(idNaoExiste);
			throw new ClienteException(idNaoExiste);
		}
		
	}
	
	

	@Override
	public void validar(UUID clienteId, byte[] conteudoFoto) throws ClienteException {		
		
		if(!existeCliente(clienteId)) {
			LOGGER.error(idNaoExiste);
			throw new ClienteException(idNaoExiste);
		}

		if (imagemTamanhoPermitido(conteudoFoto)) {
			LOGGER.error(fotoSize);
			throw new ClienteException(fotoSize);
		}

	}
	
	private Boolean existeCliente(UUID idCliente) {
		//Cliente cliente =  clienteRepository.find(idCliente);
		//return cliente != null;
		return clienteRepository.existsById(idCliente);
	}
	
	private Boolean imagemTamanhoPermitido(byte[] bytes) {
		return bytes.length > 2 * 1024 * 1024;// 2 MB em bytes
	}

}
