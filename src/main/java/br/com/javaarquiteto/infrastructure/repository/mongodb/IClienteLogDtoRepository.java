package br.com.javaarquiteto.infrastructure.repository.mongodb;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;

@Repository
public interface IClienteLogDtoRepository extends MongoRepository<ClienteLogDto, UUID>{
	
	@Query(value = "{cpf:?0}", sort="{dataHora:1}")  
	List<ClienteLogDto> getClienteLogsCpfOrderByDataHoraAsc(String cpf);	

}
