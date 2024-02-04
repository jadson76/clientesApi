package br.com.javaarquiteto.domain.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultaFotoDto {
	
	private UUID idCliente;
	
	private UUID idFoto;
	
	private byte[] foto;

}
