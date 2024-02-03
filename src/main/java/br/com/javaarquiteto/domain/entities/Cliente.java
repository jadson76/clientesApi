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
import lombok.Data;

@Entity
@Table(name = "tbl_cliente")
@Data
public class Cliente {
	
	@Id
	@Column(name="id")
	private UUID id;	

	@Column(name="nome", length = 100, nullable = false)
	private String nome;	

	@Column(name="email", length = 50, nullable = false)
	private String email;	
	
	@Column(name="cpf", length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data-nascimento" ,nullable = false)	
	private LocalDate dataNascimento;
	
	@Lob
	@Column
	private byte[] foto;
	
	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos;

}
