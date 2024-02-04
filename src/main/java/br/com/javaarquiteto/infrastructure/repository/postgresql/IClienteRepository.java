package br.com.javaarquiteto.infrastructure.repository.postgresql;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.javaarquiteto.domain.entities.sql.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, UUID>{
	
	@Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
	Cliente find(@Param("cpf") String cpf );
	
	@Query("SELECT c FROM Cliente c WHERE c.id = :id")
	Cliente find(@Param("id") UUID id );
	
	//@Query("select case when count(c) > 0 then true else false end from Cliente c where c.id = :id")
	
	@Modifying
	@Query(value="select count(*) from tbl_cliente where id = :id", nativeQuery = true)
	int existByUIID(UUID id );
	

}
