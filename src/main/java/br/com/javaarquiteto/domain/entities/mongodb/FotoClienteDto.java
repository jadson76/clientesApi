package br.com.javaarquiteto.domain.entities.mongodb;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "fotos_clientes")
public class FotoClienteDto {
	
	@Id
	private UUID id;
	
	private UUID idCliente;
	
	private byte[] foto;

}
