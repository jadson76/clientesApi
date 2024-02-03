package br.com.javaarquiteto.domain.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoCreateCommand {
	
	@NotBlank(message = "{logradouro.notblank}")
	private String logradouro;	
	
	private String complemento;

	@NotBlank(message = "{numero.notblank}")
	private String numero;	

	@NotBlank(message = "{bairro.notblank}")
	private String bairro;	
	
	@NotBlank(message = "{cidade.notblank}")
	private String cidade;	

	@NotBlank(message = "{uf.notblank}")
	private String uf;	
	
	@Pattern(regexp = "\\d{8}", message = "{cep.format}")
	@NotBlank(message = "{cep.notblank}")
	private String cep;

}
