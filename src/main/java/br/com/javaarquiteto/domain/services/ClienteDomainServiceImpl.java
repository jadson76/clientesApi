package br.com.javaarquiteto.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.EnderecoCreateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.dtos.ClienteMessageDto;
import br.com.javaarquiteto.domain.dtos.EnderecoDto;
import br.com.javaarquiteto.domain.entities.Cliente;
import br.com.javaarquiteto.domain.entities.Endereco;
import br.com.javaarquiteto.domain.enums.EstadoEnum;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteDomainService;
import br.com.javaarquiteto.domain.interfaces.IClienteValidation;
import br.com.javaarquiteto.domain.services.util.ClienteUtil;
import br.com.javaarquiteto.infrastructure.event.ClienteLogEvent;
import br.com.javaarquiteto.infrastructure.producers.ClienteMessageProducer;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IClienteRepository;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IEnderecosRepository;

@Service
public class ClienteDomainServiceImpl implements IClienteDomainService{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteDomainServiceImpl.class);
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IEnderecosRepository enderecosRepository;
	
	@Autowired
	private IClienteValidation clienteValidation;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private ClienteMessageProducer clienteMessageProducer;

	@Override
	public ClienteDto create(ClienteCreateCommand command) throws ClienteException {
		
		LOGGER.info("Inicio Inclusão de cliente");
		
		try {

			clienteValidation.validar(command);

			var novo = new Cliente();
			novo.setId(UUID.randomUUID());
			novo.setNome(command.getNome());
			novo.setCpf(command.getCpf());
			novo.setDataNascimento(command.getDataNascimento());
			novo.setEmail(command.getEmail());
			novo.setFoto(command.getFoto());

			clienteRepository.save(novo);

			List<Endereco> enderecos = createEnderecosCliente(novo, command);

			logarOperacao(novo);

			criarMensagemBoasVindas(novo);

			return getClienteDto(novo, enderecos);

		} finally {
			LOGGER.info("Fim Inclusão de cliente");
		}
	}

	

	private void logarOperacao(Cliente novo) {
		
		LOGGER.info("Inicio emitir evento inclusão de cliente");
		
		try {
		
		OperacaoEnum inclusao = novo.getFoto()!=null ? OperacaoEnum.INCLUSAO_COM_FOTO : OperacaoEnum.INCLUSAO_SEM_FOTO;		
		
		eventPublisher.publishEvent(new ClienteLogEvent(novo,inclusao));
		
		}finally {
			LOGGER.info("Fim emitir evento inclusão de cliente");
		}
		
	}

	private ClienteDto getClienteDto(Cliente novo, List<Endereco> enderecos) {		
		
		return ClienteDto.builder()
				.id(novo.getId())
				.nome(novo.getNome())
				.cpf(novo.getCpf())
				.dataNascimento(novo.getDataNascimento())
				.email(novo.getEmail())
				.enderecos(mapperEnderecos(enderecos))
				.build();
	}

	private List<EnderecoDto> mapperEnderecos(List<Endereco> enderecos) {
		modelMapper.getConfiguration()
		  .setFieldMatchingEnabled(true);
		
		return enderecos
				  .stream()
				  .map(endereco -> modelMapper.map(endereco, EnderecoDto.class))
				  .collect(Collectors.toList());
		
	}

	private List<Endereco> createEnderecosCliente(Cliente novo, ClienteCreateCommand command) {
		
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
	
	private void criarMensagemBoasVindas(Cliente novo) {
		
		LOGGER.info("Inicio enviar mensagem boas vindas inclusão de cliente para fila.");
		
		ClienteMessageDto clienteMessageDto = ClienteUtil.obterClienteMessage(novo);
		
		try {
			String message = objectMapper.writeValueAsString(clienteMessageDto);
			clienteMessageProducer.sendMessage(message);
		}catch (Exception e) {
			LOGGER.error("Falha ao enviar mensagem boas vindas inclusão de cliente para fila .", e.getMessage());
		}finally {
			LOGGER.info("Fim enviar mensagem boas vindas inclusão de cliente para fila.");
		}
		
		
	}
	
	

}
