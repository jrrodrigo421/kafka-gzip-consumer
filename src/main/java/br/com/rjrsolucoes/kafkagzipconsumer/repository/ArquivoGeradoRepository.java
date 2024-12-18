package br.com.rjrsolucoes.kafkagzipconsumer.repository;

import br.com.rjrsolucoes.kafkagzipconsumer.model.ArquivoGerado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArquivoGeradoRepository extends JpaRepository<ArquivoGerado, UUID> {
}
