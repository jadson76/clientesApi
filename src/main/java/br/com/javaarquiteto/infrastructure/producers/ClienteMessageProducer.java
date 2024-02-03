package br.com.javaarquiteto.infrastructure.producers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMessageProducer {
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteMessageProducer.class);
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;  	
	
	
	public void sendMessage(String message) {
		
		LOGGER.info("Inicio envio para fila ");
		
		rabbitTemplate.convertAndSend(queue.getName(), message);
		
		LOGGER.info("Fim envio para fila ");
	}


}
