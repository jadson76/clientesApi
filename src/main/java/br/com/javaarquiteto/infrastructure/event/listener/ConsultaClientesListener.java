package br.com.javaarquiteto.infrastructure.event.listener;

import java.time.Instant;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.infrastructure.event.ConsultaClientesEvent;
import br.com.javaarquiteto.infrastructure.repository.mongodb.IClienteLogDtoRepository;

@Component
public class ConsultaClientesListener implements ApplicationListener<ConsultaClientesEvent>{
	
private static final Logger LOGGER = LogManager.getLogger(ClienteLogListener.class);
	
	@Autowired
	private IClienteLogDtoRepository repository;

	@Override
	public void onApplicationEvent(ConsultaClientesEvent event) {
		
		LOGGER.info("Inicio listener evento consulta todos clientes.");
		
		
		OperacaoEnum operacaoEnum = (OperacaoEnum) event.getSource();			
			
		gravarLogConsultaTodosClientes(operacaoEnum);			
		
		LOGGER.info("Fim listener evento consulta todos clientes.");
		
	}
	
	private void gravarLogConsultaTodosClientes(OperacaoEnum operacaoEnum) {

		var log = ClienteLogDto.builder().id(UUID.randomUUID()).dataHora(Instant.now()).operacao(operacaoEnum.name())
				.descricao(operacaoEnum.getDescricao()).build();

		repository.save(log);

	}

}
