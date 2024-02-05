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
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.dtos.ClienteMessageDto;
import br.com.javaarquiteto.domain.dtos.ConsultaFotoDto;
import br.com.javaarquiteto.domain.dtos.EnderecoDto;
import br.com.javaarquiteto.domain.dtos.UploadResponseDto;
import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import br.com.javaarquiteto.domain.entities.mongodb.FotoClienteDto;
import br.com.javaarquiteto.domain.entities.sql.Cliente;
import br.com.javaarquiteto.domain.entities.sql.Endereco;
import br.com.javaarquiteto.domain.enums.OperacaoEnum;
import br.com.javaarquiteto.domain.exceptions.ClienteException;
import br.com.javaarquiteto.domain.interfaces.IClienteDomainService;
import br.com.javaarquiteto.domain.interfaces.IClienteValidation;
import br.com.javaarquiteto.domain.interfaces.IEnderecoDomainService;
import br.com.javaarquiteto.domain.services.util.ClienteUtil;
import br.com.javaarquiteto.infrastructure.event.ClienteLogEvent;
import br.com.javaarquiteto.infrastructure.event.ConsultaClientesEvent;
import br.com.javaarquiteto.infrastructure.producers.ClienteMessageProducer;
import br.com.javaarquiteto.infrastructure.repository.mongodb.IFotoClienteDtoRepository;
import br.com.javaarquiteto.infrastructure.repository.postgresql.IClienteRepository;

@Service
public class ClienteDomainServiceImpl implements IClienteDomainService{
	
	private static final Logger LOGGER = LogManager.getLogger(ClienteDomainServiceImpl.class);
	
	@Autowired
	private IClienteRepository clienteRepository;	
	
	@Autowired
	private IFotoClienteDtoRepository fotoClienteRepository;
	
	@Autowired
	private IClienteValidation clienteValidation;
	
	@Autowired
	private IEnderecoDomainService enderecoDomainService;
	
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

			clienteRepository.save(novo);

			List<Endereco> enderecos = createEnderecosCliente(novo, command);
			
			Boolean possuiFoto = createFotoCliente(novo, command);

			logarOperacao(novo, possuiFoto);

			criarMensagemBoasVindas(novo);

			return getClienteDto(novo, enderecos);

		} finally {
			LOGGER.info("Fim Inclusão de cliente");
		}
	}

	

	private Boolean createFotoCliente(Cliente novo, ClienteCreateCommand command) {
		
		LOGGER.info("Inicio gravar foto na inclusão de cliente");

		try {

			if (command.getFoto() != null) {
				var foto = FotoClienteDto.builder().id(UUID.randomUUID()).idCliente(novo.getId())
						.foto(command.getFoto()).build();
				fotoClienteRepository.save(foto);

				return true;
			}

			return false;

		} finally {
			LOGGER.info("Fim gravar foto na inclusão de cliente");
		}
		
		
	}
	
	private void logarOperacao(Cliente cliente, Boolean possuiFoto) {
		
		OperacaoEnum inclusao = possuiFoto ? OperacaoEnum.INCLUSAO_COM_FOTO : OperacaoEnum.INCLUSAO_SEM_FOTO;		
		
		logarOperacao(cliente, inclusao);
		
	}



	private void logarOperacao(Cliente cliente, OperacaoEnum operacaoEnum){
		
		LOGGER.info("Inicio emitir evento inclusão de cliente");
		
		try {			
			
			if(operacaoEnum.equals(OperacaoEnum.CONSULTA_TODOS_CLIENTES)) {
				eventPublisher.publishEvent(new ConsultaClientesEvent(operacaoEnum));
			}else {
				eventPublisher.publishEvent(new ClienteLogEvent(cliente,operacaoEnum));
			}	
			
		
		}finally {
			LOGGER.info("Fim emitir evento inclusão de cliente");
		}
		
	}

	private ClienteDto getClienteDto(Cliente cliente, List<Endereco> enderecos) {		
		
		return ClienteDto.builder()
				.id(cliente.getId())
				.nome(cliente.getNome())
				.cpf(cliente.getCpf())
				.dataNascimento(cliente.getDataNascimento())
				.email(cliente.getEmail())
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
		
		LOGGER.info("Inicio chama serviço para gravar endereço(s) do cliente.");
		
		try {

			return enderecoDomainService.createEnderecosCliente(novo, command);

		} finally {
			LOGGER.info("Fim chama serviço para gravar endereço(s) do cliente.");
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



	@Override
	public ClienteDto update(ClienteUpdateCommand command) throws ClienteException {
		
		LOGGER.info("Inicio atualializar registro de cliente.");
		
		try {
			
			clienteValidation.validar(command);
			
			var updateCliente = clienteRepository.find(command.getId());			
			
			updateCliente.setNome(command.getNome());	
			updateCliente.setDataNascimento(command.getDataNascimento());
			updateCliente.setEmail(command.getEmail());
			
			clienteRepository.save(updateCliente);

			List<Endereco> enderecos = atualizaEnderecosCliente(updateCliente, command);

			logarOperacao(updateCliente,OperacaoEnum.ATUALIZACAO_INFORMACOES);			

			return getClienteDto(updateCliente, enderecos);		
		
		}finally {
			LOGGER.info("Fim atualizar registro de cliente.");
		}
	
	}



	private List<Endereco> atualizaEnderecosCliente(Cliente updateCliente, ClienteUpdateCommand command) {
		
		LOGGER.info("Inicio chama serviço para atualizar endereço(s) do cliente.");
		
		try {

			return enderecoDomainService.atualizaEnderecosCliente(updateCliente, command);

		} finally {
			LOGGER.info("Fim chama serviço para atualizar endereço(s) do cliente.");
		}		
		
	}



	@Override
	public ClienteDto delete(UUID idCliente) throws ClienteException {

		LOGGER.info("Inicio remover registro de cliente.");
		
		try {
			
			clienteValidation.validar(idCliente);
			
			Cliente cliente = clienteRepository.find(idCliente);
			
			List<Endereco> enderecos = removerEnderecosCliente(idCliente);
			
			clienteRepository.deleteById(cliente.getId());			

			logarOperacao(cliente,OperacaoEnum.EXCLUSAO);			

			return getClienteDto(cliente, enderecos);		
		
		}finally {
			LOGGER.info("Fim remover registro de cliente.");
		}
	}



	private List<Endereco> removerEnderecosCliente(UUID idCliente) {
		LOGGER.info("Inicio chama serviço para remover todos os endereço(s) do cliente.");
		
		try {

			return enderecoDomainService.removerEnderecosCliente(idCliente);

		} finally {
			LOGGER.info("Fim chama serviço para atualizar endereço(s) do cliente.");
		}		
		
	}
	

	@Override
	public ClienteDto getById(UUID idCliente) throws ClienteException {
		LOGGER.info("Inicio consultar registro de cliente pelo ID.");
		
		try {
			
			clienteValidation.validar(idCliente);
			
			Cliente cliente = clienteRepository.find(idCliente);
			
			List<Endereco> enderecos = consultarEnderecosCliente(idCliente);					

			logarOperacao(cliente,OperacaoEnum.CONSULTA_CLIENTE);			

			return getClienteDto(cliente, enderecos);		
		
		}finally {
			LOGGER.info("Fim consultar registro de cliente pelo ID.");
		}
	}



	private List<Endereco> consultarEnderecosCliente(UUID idCliente) {
		LOGGER.info("Inicio chama serviço para consultar todos os endereço(s) do cliente.");
		
		try {

			return enderecoDomainService.consultarEnderecosCliente(idCliente);

		} finally {
			LOGGER.info("Fim chama serviço para consultar todos endereço(s) do cliente.");
		}	
	}



	@Override
	public List<ClienteDto> getAll() {
		LOGGER.info("Inicio consultar todos registros de cliente.");
		
		try {			
			
			List<Cliente> clientes = clienteRepository.findAll();
			
			consultarEnderecosCliente(clientes);						

			logarOperacao(null,OperacaoEnum.CONSULTA_TODOS_CLIENTES);			

			return getClientesDto(clientes);		
		
		}finally {
			LOGGER.info("Fim consultar todos registros de cliente.");
		}
	}



	private List<ClienteDto> getClientesDto(List<Cliente> clientes) {
		
		List<ClienteDto> clientesDto = new ArrayList<ClienteDto>();
		
		for(Cliente cliente : clientes) {
			var clienteDto = ClienteDto.builder()
					.id(cliente.getId())
					.nome(cliente.getNome())
					.cpf(cliente.getCpf())
					.dataNascimento(cliente.getDataNascimento())
					.email(cliente.getEmail())
					.enderecos(mapperEnderecos(cliente.getEnderecos()))
					.build();
			clientesDto.add(clienteDto);
		}
		
		return clientesDto;
		
	}



	private void consultarEnderecosCliente(List<Cliente> clientes) {
		LOGGER.info("Inicio chama serviço para consultar todos os endereço(s) do cliente.");
		
		try {
			
			for(Cliente cliente : clientes) {
				List<Endereco> enderecos = enderecoDomainService.consultarEnderecosCliente(cliente.getId());
				cliente.getEnderecos().addAll(enderecos);
			}		

		} finally {
			LOGGER.info("Fim chama serviço para consultar todos endereço(s) do cliente.");
		}	
	}



	@Override
	public UploadResponseDto updateFoto(UUID idCliente, byte[] conteudoFoto) throws ClienteException {
		LOGGER.info("Inicio atualializar registro foto de cliente.");
		
		try {
			
			FotoClienteDto foto = null;
			
			clienteValidation.validar(idCliente, conteudoFoto);
			
			var cliente = clienteRepository.find(idCliente);			
			
			var fotoOp = fotoClienteRepository.getFotoClientebyIdCliente(idCliente);
			
			if(fotoOp.isPresent()) {
				foto = fotoOp.get();
				foto.setFoto(conteudoFoto);
			}else {
				foto = FotoClienteDto.builder()
						.id(idCliente)
						.idCliente(idCliente)
						.foto(conteudoFoto)
						.build();
			}	
			
			fotoClienteRepository.save(foto);		

			logarOperacao(cliente,OperacaoEnum.ATUALIZACAO_FOTO);			

			return new UploadResponseDto(true);	
		
		}finally {
			LOGGER.info("Fim atualizar registro de cliente.");
		}
	}



	@Override
	public ConsultaFotoDto obterFoto(UUID idCliente) throws ClienteException {
		
		LOGGER.info("Inicio consultar registro foto de cliente.");
		
		try {
		
		clienteValidation.validar(idCliente);
		
		FotoClienteDto foto = fotoClienteRepository.getFotoClientebyIdCliente(idCliente)
				.orElseThrow(() -> 	new ClienteException("Cliente não possui registro de foto"));	
	
		
		return ConsultaFotoDto.builder()
				.idFoto(foto.getId())
				.idCliente(idCliente)
				.foto(foto.getFoto())
				.build();
		
		}finally {
			LOGGER.info("Fim consultar registro foto de cliente.");
		}
	
	}



	

}
