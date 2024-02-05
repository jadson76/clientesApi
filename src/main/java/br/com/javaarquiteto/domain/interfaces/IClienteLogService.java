package br.com.javaarquiteto.domain.interfaces;

import java.util.UUID;

import br.com.javaarquiteto.domain.dtos.ClienteLogResponseDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public interface IClienteLogService {
	
	ClienteLogResponseDto obterLog(UUID idCliente) throws ClienteException;

}
