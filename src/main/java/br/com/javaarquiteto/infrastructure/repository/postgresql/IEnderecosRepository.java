package br.com.javaarquiteto.infrastructure.repository.postgresql;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.javaarquiteto.domain.entities.Endereco;

public interface IEnderecosRepository extends JpaRepository<Endereco, UUID>{

}
