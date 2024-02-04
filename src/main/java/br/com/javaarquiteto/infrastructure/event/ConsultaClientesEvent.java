package br.com.javaarquiteto.infrastructure.event;

import org.springframework.context.ApplicationEvent;

public class ConsultaClientesEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public ConsultaClientesEvent(Object source) {
		super(source);		
	}

}
