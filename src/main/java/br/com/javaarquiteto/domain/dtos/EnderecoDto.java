package br.com.javaarquiteto.domain.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class EnderecoDto {
	
	private UUID id;
	
	private String logradouro;	
	
	private String complemento;	

	private String numero;	

	private String bairro;	

	private String cidade;	

	private String uf;	

	private String cep;

}
