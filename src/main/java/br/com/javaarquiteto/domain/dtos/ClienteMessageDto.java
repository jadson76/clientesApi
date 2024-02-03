package br.com.javaarquiteto.domain.dtos;

import java.util.UUID;

public record ClienteMessageDto(UUID clienteId, String emailTo,String subject,String body) {

}
