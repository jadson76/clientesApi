package br.com.javaarquiteto.application.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.javaarquiteto.domain.commands.ClienteCreateCommand;
import br.com.javaarquiteto.domain.commands.ClienteUpdateCommand;
import br.com.javaarquiteto.domain.commands.EnderecoCreateCommand;
import br.com.javaarquiteto.domain.commands.EnderecoUpdateCommand;
import br.com.javaarquiteto.domain.dtos.ClienteDto;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientesResourceTest {
	
	@Autowired
	MockMvc mockMvc;	

	@Autowired
	ObjectMapper objectMapper;
	
	static UUID idCliente;
	static UUID idEndereco;
	static String cpfCliente;
	
	@Test
	@Order(1)
	public void postClienteSucessoTest() throws JsonProcessingException, Exception {		 

		Faker faker = new Faker();

		ClienteCreateCommand request = new ClienteCreateCommand();
		request.setNome(faker.name().fullName());
		request.setEmail("biojadson@gmail.com");
		request.setCpf(TesteUtil.getValorRandomico(11));
		request.setDataNascimento(TesteUtil.getDataNascimentoRamdomica(1950, 2010));

		EnderecoCreateCommand endereco = new EnderecoCreateCommand(faker.address().streetPrefix(),
				faker.address().buildingNumber(), faker.address().streetAddressNumber(), TesteUtil.getBairro(),
				faker.address().cityName(), TesteUtil.getUFRamdomica(), TesteUtil.getValorRandomico(8));
		request.setEnderecos(List.of(endereco));

		MvcResult result = mockMvc.perform(
				post("/api/clientes").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		
	
		Value format = JsonFormat.Value.forPattern("dd/MM/yyyy");
		objectMapper.configOverride(LocalDate.class).setFormat(format);

		ClienteDto response = objectMapper.readValue(contentAsString, ClienteDto.class);

		idCliente = response.getId();
		cpfCliente = request.getCpf();
		idEndereco = response.getEnderecos().get(0).getId();
		
	}
	
	@Test
	@Order(2)
	public void putClienteSucessoTest() throws JsonProcessingException, Exception {		 

		Faker faker = new Faker();

		ClienteUpdateCommand request = new ClienteUpdateCommand();
		request.setId(idCliente);
		request.setNome(faker.name().fullName());
		request.setEmail("biojadson@gmail.com");		
		request.setDataNascimento(TesteUtil.getDataNascimentoRamdomica(1950, 2010));

		EnderecoUpdateCommand endereco = new EnderecoUpdateCommand(idEndereco, faker.address().streetPrefix(),
				faker.address().buildingNumber(), faker.address().streetAddressNumber(), TesteUtil.getBairro(),
				faker.address().cityName(), TesteUtil.getUFRamdomica(), TesteUtil.getValorRandomico(8));
		request.setEnderecos(List.of(endereco));

		MvcResult result = mockMvc.perform(
				put("/api/clientes").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		
	
		Value format = JsonFormat.Value.forPattern("dd/MM/yyyy");
		objectMapper.configOverride(LocalDate.class).setFormat(format);

		ClienteDto response = objectMapper.readValue(contentAsString, ClienteDto.class);

		idCliente = response.getId();
		
		
	}
	
	@Test
	@Order(3)
	public void getAllClientesSucessoTest() throws JsonProcessingException, Exception {			

		MvcResult result = mockMvc.perform(
				get("/api/clientes").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		
	
		Value format = JsonFormat.Value.forPattern("dd/MM/yyyy");
		objectMapper.configOverride(LocalDate.class).setFormat(format);

		List<ClienteDto> response = objectMapper.readValue(contentAsString, new TypeReference<List<ClienteDto>>() {});

		assertNotNull(response);	
		
		
	}
	
	@Test
	@Order(4)
	public void getClientebyIdSucessoTest() throws JsonProcessingException, Exception {			

		MvcResult result = mockMvc.perform(get("/api/clientes/" + idCliente)
                  .contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.id").exists())                 
                  .andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		
	
		Value format = JsonFormat.Value.forPattern("dd/MM/yyyy");
		objectMapper.configOverride(LocalDate.class).setFormat(format);

		ClienteDto response = objectMapper.readValue(contentAsString, ClienteDto.class);

		assertNotNull(response);	
		
		
	}
	
	@Test
	@Order(5)
	public void updateFotoByIDSucessoTest() throws JsonProcessingException, Exception {
		
		byte[] conteudoFoto = TesteUtil.getFakeFoto();

		 mockMvc.perform(
				put("/api/clientes/foto/" + idCliente).contentType("application/json").content(objectMapper.writeValueAsString(conteudoFoto)))
				.andExpect(status().isCreated());
		
		
	}
	
	@Test
	@Order(6)
	public void deleteClientebyIdSucessoTest() throws JsonProcessingException, Exception {			

		   mockMvc.perform(delete("/api/clientes/" + idCliente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()) ;       	
		
		
	}

}
