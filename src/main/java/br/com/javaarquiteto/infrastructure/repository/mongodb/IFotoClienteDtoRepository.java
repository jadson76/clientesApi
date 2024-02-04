package br.com.javaarquiteto.infrastructure.repository.mongodb;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.javaarquiteto.domain.entities.mongodb.FotoClienteDto;

@Repository
public interface IFotoClienteDtoRepository extends MongoRepository<FotoClienteDto, UUID>{
	
	@Query(value = "{idCliente:?0}")  
	Optional<FotoClienteDto> getFotoClientebyIdCliente(UUID idCliente);	
	
	@ExistsQuery("{ 'idCliente': ?0}")
	boolean existByClienteId(UUID idCliente);

}
