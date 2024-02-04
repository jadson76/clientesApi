package br.com.javaarquiteto.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.commands.EnderecoCreateCommand;
import br.com.javaarquiteto.domain.commands.EnderecoUpdateCommand;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.entities.sql.Endereco;
import br.com.javaarquiteto.domain.enums.EstadoEnum;
import br.com.javaarquiteto.domain.interfaces.IEnderecoDomainService;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IEnderecosRepository;

@Service
public class EnderecoDomainServiceImpl implements IEnderecoDomainService {
	
	private static final Logger LOGGER = LogManager.getLogger(EnderecoDomainServiceImpl.class);
	
	@Autowired
	private IEnderecosRepository enderecosRepository;

	@Override
	public List<Endereco> createEnderecosCliente(Cliente novo, ClienteCreateCommand command) {
		
	LOGGER.info("Inicio gravar endereço(s) do cliente.");
		
		try {

			List<Endereco> enderecos = new ArrayList<Endereco>();

			for (EnderecoCreateCommand enderecoCommand : command.getEnderecos()) {
				var endereco = Endereco.builder().id(UUID.randomUUID()).logradouro(enderecoCommand.getLogradouro())
						.numero(enderecoCommand.getNumero()).complemento(enderecoCommand.getComplemento())
						.bairro(enderecoCommand.getBairro()).cidade(enderecoCommand.getCidade())
						.cep(enderecoCommand.getCep()).uf(EstadoEnum.getEstadoEnum(enderecoCommand.getUf()))
						.cliente(novo).build();
				enderecosRepository.save(endereco);
				enderecos.add(endereco);
			}

			return enderecos;

		} finally {
			LOGGER.info("Fim gravar endereço(s) do cliente.");
		}	
	}

	@Override
	public List<Endereco> atualizaEnderecosCliente(Cliente updateCliente, ClienteUpdateCommand command) {
		
		LOGGER.info("Inicio atualizar endereço(s) do cliente.");

		try {

			List<Endereco> enderecos = new ArrayList<Endereco>();

			for (EnderecoUpdateCommand enderecoCommand : command.getEnderecos()) {
				
				var enderecoAtual = enderecosRepository.findById(enderecoCommand.getId()).get();
				enderecoAtual.setNumero(enderecoCommand.getNumero());
				enderecoAtual.setComplemento(enderecoCommand.getComplemento());
				enderecoAtual.setBairro(enderecoCommand.getBairro());
				enderecoAtual.setCidade(enderecoCommand.getCidade());
				enderecoAtual.setUf(EstadoEnum.getEstadoEnum(enderecoCommand.getUf()));
				enderecoAtual.setCep(enderecoCommand.getCep());
				
				enderecosRepository.save(enderecoAtual);
				enderecos.add(enderecoAtual);
			}

			return enderecos;

		} finally {
			LOGGER.info("Fim atualizar endereço(s) do cliente.");
		}
	}

	@Override
	public List<Endereco> removerEnderecosCliente(UUID idCliente) {
		LOGGER.info("Inicio remover endereço(s) do cliente.");

		try {

			List<Endereco> enderecos = enderecosRepository.findAllByClienteId(idCliente);
			enderecosRepository.deleteAll(enderecos);			
			return enderecos;

		} finally {
			LOGGER.info("Fim remover endereço(s) do cliente.");
		}
	}

	@Override
	public List<Endereco> consultarEnderecosCliente(UUID idCliente) {
		LOGGER.info("Inicio consultar todos endereço(s) do cliente.");

		try {

			return enderecosRepository.findAllByClienteId(idCliente);	

		} finally {
			LOGGER.info("Fim consultar endereço(s) do cliente.");
		}
	}	
	

}
