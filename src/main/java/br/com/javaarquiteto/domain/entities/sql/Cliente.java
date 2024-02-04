package br.com.javaarquiteto.domain.entities.sql;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "tbl_cliente")
@Data
public class Cliente implements Persistable<UUID>{
	
	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
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
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos;

	@Override
	public boolean isNew() {		
		return id == null;
	}

}
