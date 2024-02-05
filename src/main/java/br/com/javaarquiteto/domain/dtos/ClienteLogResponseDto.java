package br.com.javaarquiteto.domain.dtos;

import java.util.ArrayList;
import java.util.List;

import br.com.javaarquiteto.domain.entities.mongodb.ClienteLogDto;
import lombok.Data;

@Data
public class ClienteLogResponseDto {
	
	private List<ClienteLogDto> logs = new ArrayList<>();

}
