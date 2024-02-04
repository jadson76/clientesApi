package br.com.javaarquiteto.domain.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {	

	private UUID id;	

	private String nome;
	
	private String email;
	
	private String cpf;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	private List<EnderecoDto> enderecos;	
	

}
