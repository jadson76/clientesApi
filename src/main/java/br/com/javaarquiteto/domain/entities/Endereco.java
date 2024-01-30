package br.com.javaarquiteto.domain.entities;

import java.util.UUID;

import br.com.javaarquiteto.domain.enums.EstadoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "tbl_endereco")
@Data
public class Endereco {
	
	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="logradouro", length = 100, nullable = false)
	private String logradouro;
	
	@Column(name="complemento", length = 200, nullable = false)
	private String complemento;
	
	@Column(name="numero", length = 100, nullable = false)
	private String numero;
	
	@Column(name="bairro", length = 100, nullable = false)
	private String bairro;
	
	@Column(name="cidade", length = 100, nullable = false)	
	private String cidade;
	
	@Enumerated(EnumType.STRING)
	@Column(name="uf", length = 2, nullable = false)
	private EstadoEnum uf;
	
	@Pattern(regexp = "\\d{11}", message = "O CEP deve conter apenas números e ter 8 dígitos")
	@Column(name="cep", length = 8, nullable = false)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;


}
