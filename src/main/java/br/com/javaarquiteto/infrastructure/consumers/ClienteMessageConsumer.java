package br.com.javaarquiteto.infrastructure.consumers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javaarquiteto.domain.dtos.ClienteMessageDto;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.infrastructure.component.MailSenderComponent;
import br.com.javaarquiteto.infrastructure.event.EmailSenderLogEvent;



@Service
public class ClienteMessageConsumer {
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteMessageConsumer.class);
	
	@Autowired
	MailSenderComponent mailSenderComponent;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	ObjectMapper objectMapper;	
	
	@RabbitListener(queues = { "${queue.name}" })
	public void receive(@Payload String message) {
		
		LOGGER.info("Inicio ler fila cadastro de cliente");
		
		try {
			ClienteMessageDto dto = objectMapper.readValue(message, ClienteMessageDto.class);
			
			mailSenderComponent.sendMessage(dto.emailTo(), dto.subject(), dto.body());
			
			logarOperacao(dto);
			
		} catch (JsonProcessingException e) {
			
			LOGGER.error("Falha ao ler fila de cadastro de cliente.", e.getMessage());
			
		} finally {
			
			LOGGER.info("Fim ler fila cadastro de cliente");
			
		}		
		
	}


	private void logarOperacao(ClienteMessageDto dto) {		
		
		eventPublisher.publishEvent(new EmailSenderLogEvent(dto,OperacaoEnum.ENVIO_EMAIL));
		
	}

}
