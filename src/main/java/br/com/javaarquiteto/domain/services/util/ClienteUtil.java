package br.com.javaarquiteto.domain.services.util;


import br.com.javaarquiteto.domain.dtos.ClienteMessageDto;
import br.com.javaarquiteto.domain.entities.Cliente;

public class ClienteUtil {

	private static final String SUBJECT = "Conta criada com sucesso - API de Clientes.";

	public static ClienteMessageDto obterClienteMessage(Cliente cliente) {
		
		StringBuilder corpoMensagem = new StringBuilder();
		corpoMensagem.append("<div>");
		corpoMensagem.append("<p>Parabens " + cliente.getNome() + ", sua conta de usuario foi criada com sucesso. </p>");
		corpoMensagem.append("<p>att, </p>");
		corpoMensagem.append("<p>Equipe de Boas Vindas. </p>");
		corpoMensagem.append("</div>");
		
		return new ClienteMessageDto(cliente.getId(),cliente.getEmail(),SUBJECT,corpoMensagem.toString());

		
	}

}
