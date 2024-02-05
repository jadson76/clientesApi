package br.com.javaarquiteto.domain.services;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.javaarquiteto.domain.dtos.ClienteLogResponseDto;
import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteLogService;
import br.com.javaarquiteto.domain.interfaces.IClienteValidation;
import br.com.javaarquiteto.infrastructure.repository.mongodb.IClienteLogDtoRepository;

@Service
public class ClienteLogServiceImpl implements IClienteLogService{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteLogServiceImpl.class);
	
	@Autowired	
	private IClienteValidation clienteValidation;
	@Autowired
	private IClienteLogDtoRepository clienteLogRepository;

	@Override
	public ClienteLogResponseDto obterLog(UUID idCliente) throws ClienteException {
		LOGGER.info("Inicio consultar registro log de cliente.");
		
		try {
		
			clienteValidation.validar(idCliente);
		
			ClienteLogResponseDto responseDto = new ClienteLogResponseDto();
		
			List<ClienteLogDto> logs = clienteLogRepository.getClienteLogsIdClienteOrderByDataHoraAsc(idCliente);
		
			responseDto.getLogs().addAll(logs);
		
			return responseDto; 
		
		}finally {
			LOGGER.info("Fim consultar registro log de cliente.");
		}
	
	}

}
