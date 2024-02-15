package br.com.javaarquiteto.infrastructure.event.listener;

import java.time.Instant;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.dtos.ClienteMessageDto;
import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.infrastructure.event.EmailSenderLogEvent;
import br.com.javaarquiteto.infrastructure.repository.mongodb.IClienteLogDtoRepository;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IClienteRepository;

@Component
public class EmailSenderLogListener implements ApplicationListener<EmailSenderLogEvent>{
	
	private static final Logger LOGGER = LogManager.getLogger(EmailSenderLogListener.class);
	
	@Autowired
	private IClienteLogDtoRepository repository;
	
	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	public void onApplicationEvent(EmailSenderLogEvent event) {
		
		LOGGER.info("Inicio criar evento envio de e-mail cadastro de cliente.");
		
		ClienteMessageDto clienteMessage = (ClienteMessageDto) event.getSource();
		
		gravarLog(clienteMessage , event.getOperacaoEnum());
		
		LOGGER.info("Fim criar evento envio de e-mail cadastro de cliente.");
		
	}

	private void gravarLog(ClienteMessageDto clienteMessage, OperacaoEnum operacaoEnum) {
		
		try {

			Cliente cliente = clienteRepository.find(clienteMessage.clienteId());

			String descricaoNaoformatada = operacaoEnum.getDescricao();

			String descricaoFormatada = String.format(descricaoNaoformatada, cliente.getNome(), cliente.getEmail());

			var log = ClienteLogDto.builder().id(UUID.randomUUID()).idCliente(cliente.getId()).cpf(cliente.getCpf())
					.dataHora(Instant.now()).operacao(operacaoEnum.name()).descricao(descricaoFormatada).build();

			repository.save(log);

		} catch (Exception e) {
			LOGGER.info("Erro - onApplicationEvent : " +e.getMessage());
		}

	}

}
