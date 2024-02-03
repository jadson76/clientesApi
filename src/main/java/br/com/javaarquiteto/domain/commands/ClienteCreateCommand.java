package br.com.javaarquiteto.domain.commands;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteCreateCommand {


	@Size(min = 8, max = 100, message = "{nome.notblank}")
	@NotBlank(message = "{nome.size}")
	private String nome;	

	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "{email.format}")
	@NotBlank(message = "{email.notblank}")
	private String email;	

	@Pattern(regexp = "\\d{11}", message = "{cpf.format}")
	@NotBlank(message = "{cpf.blank}")
	private String cpf;	

	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "{data.nasc.notnull}")
	private LocalDate dataNascimento;	

	private byte[] foto;	

	@NotEmpty(message = "{email.notempty}")
	private List<EnderecoCreateCommand> enderecos;

   
	

}
