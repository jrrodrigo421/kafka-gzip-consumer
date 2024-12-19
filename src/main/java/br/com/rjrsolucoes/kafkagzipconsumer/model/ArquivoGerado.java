package br.com.rjrsolucoes.kafkagzipconsumer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "arquivos_gerados")
public class ArquivoGerado {

  @Id
  private UUID id;

  @Column(name = "data_hora_criacao", nullable = false)
  private LocalDateTime dataHoraCriacao;

  @Column(name = "mensagens", nullable = false, columnDefinition = "TEXT")
  private String mensagens;

  @Column(name = "nome_arquivo", nullable = false)
  private String nomeArquivo;

  public ArquivoGerado() {
    this.id = UUID.randomUUID();
    this.dataHoraCriacao = LocalDateTime.now();
  }

  public ArquivoGerado(String mensagens, String nomeArquivo) {
    this();
    this.mensagens = mensagens;
    this.nomeArquivo = nomeArquivo;
  }

  public UUID getId() {
    return id;
  }

  public LocalDateTime getDataHoraCriacao() {
    return dataHoraCriacao;
  }

  public String getMensagens() {
    return mensagens;
  }

  public String getNomeArquivo() {
    return nomeArquivo;
  }

  public void setMensagens(String mensagens) {
    this.mensagens = mensagens;
  }

  public void setNomeArquivo(String nomeArquivo) {
    this.nomeArquivo = nomeArquivo;
  }
}
