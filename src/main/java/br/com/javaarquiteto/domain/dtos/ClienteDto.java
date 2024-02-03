package br.com.javaarquiteto.domain.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "clientes")
public class ClienteDto {
	
	@Id
	private UUID id;	

	private String nome;
	
	private String email;
	
	private String cpf;
	
	private LocalDate dataNascimento;
	
	private List<EnderecoDto> enderecos;	
	

}
