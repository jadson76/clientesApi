package br.com.javaarquiteto.infrastructure.event.listener;

import java.time.Instant;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.infrastructure.event.ClienteLogEvent;
import br.com.javaarquiteto.infrastructure.repository.mongodb.IClienteLogDtoRepository;

@Component
public class ClienteLogListener implements ApplicationListener<ClienteLogEvent> {
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteLogListener.class);
	
	@Autowired
	private IClienteLogDtoRepository repository;

	@Override
	public void onApplicationEvent(ClienteLogEvent event) {
		
		LOGGER.info("Inicio listener evento cadastro de cliente.");
		
		
		Cliente cliente = (Cliente) event.getSource();			
		
		gravarLogCliente(cliente , event.getOperacaoEnum());
				
		LOGGER.info("Fim listener evento cadastro de cliente.");
		
	}	

	private void gravarLogCliente(Cliente cliente, OperacaoEnum operacaoEnum) {
		
		String descricaoNaoformatada = operacaoEnum.getDescricao();
		
		String descricaoFormatada = String.format(descricaoNaoformatada, cliente.getNome() , cliente.getCpf());
		
		var log = ClienteLogDto.builder()
				.id(UUID.randomUUID())
				.idCliente(cliente.getId())
				.cpf(cliente.getCpf())				
				.dataHora(Instant.now())
				.operacao(operacaoEnum.name())
				.descricao(descricaoFormatada)
				.build();
		
		repository.save(log);		
	}

}
