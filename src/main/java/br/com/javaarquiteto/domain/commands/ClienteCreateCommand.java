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


	@Size(min = 8, max = 100, message = "O nome deve ter entre 8 e 100 caracteres.")
	@NotBlank(message = "Nome é obrigatorio.")
	private String nome;	

	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "O email deve conter um formato valido.")
	@NotBlank(message = "e-mail é obrigatorio.")
	private String email;	

	@Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números e ter 11 dígitos")
	@NotBlank(message = "CPF é obrigatorio.")
	private String cpf;	

	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Data de Nascimento é obrigatoria.")
	private LocalDate dataNascimento;	

	private byte[] foto;	

	@NotEmpty(message = "Deve incluir pelo menos um endereço valido.")
	private List<EnderecoCreateCommand> enderecos;

   
	

}
