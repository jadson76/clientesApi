package br.com.javaarquiteto.domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tbl_cliente")
@Data
public class Cliente {
	
	@Id
	@Column(name="id")
	private UUID id;
	
	@Size(min = 8, max = 100, message = "O nome deve ter entre 8 e 100 caracteres.")
	@Column(name="nome", length = 100, nullable = false)
	private String nome;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "O email deve conter um formato valido.")
	@Column(name="email", length = 50, nullable = false, unique = true)
	private String email;
	
	@Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números e ter 11 dígitos")
	@Column(name="cpf", length = 11, nullable = false)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data-nascimento" ,nullable = false)	
	private LocalDate date;
	
	@Lob
	@Column(name="foto" , columnDefinition="BLOB")	
	private byte[] foto;
	
	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos;

}
