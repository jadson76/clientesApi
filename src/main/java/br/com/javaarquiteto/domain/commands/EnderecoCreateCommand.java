package br.com.javaarquiteto.domain.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoCreateCommand {
	
	@NotBlank(message = "logradouro é obrigatorio.")
	private String logradouro;	
	
	private String complemento;

	@NotBlank(message = "Numero é obrigatorio.")
	private String numero;	

	@NotBlank(message = "Bairro é obrigatorio.")
	private String bairro;	
	
	@NotBlank(message = "Cidade é obrigatorio.")
	private String cidade;	

	@NotBlank(message = "UF é obrigatorio.")
	private String uf;	
	
	@Pattern(regexp = "\\d{8}", message = "O CEP deve conter apenas números e ter 8 dígitos")
	@NotBlank(message = "CEP é obrigatorio.")
	private String cep;

}
