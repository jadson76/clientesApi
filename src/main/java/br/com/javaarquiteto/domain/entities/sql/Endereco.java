package br.com.javaarquiteto.domain.entities.sql;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import br.com.javaarquiteto.domain.enums.EstadoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco implements Persistable<UUID> {
	
	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name="id")
	private UUID id;
	
	@Column(name="logradouro", length = 100, nullable = false)
	private String logradouro;
	
	@Column(name="complemento", length = 200, nullable = true)
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

	@Column(name="cep", length = 8, nullable = false)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@Override
	public boolean isNew() {		
		return id == null;
	}


}
