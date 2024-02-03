package br.com.javaarquiteto.domain.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(HttpStatus status,  List<String> errors) {

}
