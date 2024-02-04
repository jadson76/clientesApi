package br.com.javaarquiteto.domain.entities.mongodb;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "cliente_logs")
public class ClienteLogDto {
	
	@Id
	private UUID id;
	
	private UUID idCliente;
	
	private String cpf;
	
	private Instant dataHora;
	
	private String operacao;
	
	private String descricao;

}
