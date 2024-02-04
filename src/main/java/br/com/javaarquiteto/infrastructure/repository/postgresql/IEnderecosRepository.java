package br.com.javaarquiteto.infrastructure.repository.postgresql;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.javaarquiteto.domain.entities.sql.Endereco;

public interface IEnderecosRepository extends JpaRepository<Endereco, UUID>{
	
	@Query("SELECT e FROM Endereco e WHERE e.cliente.id = :id")
	List<Endereco> findAllByClienteId(@Param("id") UUID idCliente );

}
