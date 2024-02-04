package br.com.javaarquiteto.infrastructure.event;

import org.springframework.context.ApplicationEvent;

import br.com.javaarquiteto.domain.enums.OperacaoEnum;

public class ClienteLogEvent extends ApplicationEvent{	
	
	private static final long serialVersionUID = 1L;
	
	private OperacaoEnum operacaoEnum;

	public ClienteLogEvent(Object source, OperacaoEnum operacaoEnum) {
		super(source);
		this.operacaoEnum = operacaoEnum;
	}


	public OperacaoEnum getOperacaoEnum() {
		return operacaoEnum;
	}	
	
	
	

}
