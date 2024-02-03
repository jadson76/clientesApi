package br.com.javaarquiteto.infrastructure.repository.postgresql;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.javaarquiteto.domain.entities.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, UUID>{
	
	@Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
	Cliente find(@Param("cpf") String cpf );
	
	@Query("SELECT c FROM Cliente c WHERE c.id = :id")
	Cliente find(@Param("id") UUID id );

}
