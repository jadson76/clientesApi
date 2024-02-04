package br.com.javaarquiteto.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.entities.sql.Endereco;

public interface IEnderecoDomainService {
	
	List<Endereco> createEnderecosCliente(Cliente novo, ClienteCreateCommand command);

	List<Endereco> atualizaEnderecosCliente(Cliente updateCliente, ClienteUpdateCommand command);

	List<Endereco> removerEnderecosCliente(UUID idCliente);

	List<Endereco> consultarEnderecosCliente(UUID idCliente);

}
