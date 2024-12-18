package br.com.rjrsolucoes.kafkagzipconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic}")
  private String kafkaTopic;

  @PostMapping("/send")
  public String sendMessage(@RequestBody String message) {
    kafkaTemplate.send(kafkaTopic, message);
    return "Mensagem enviada com sucesso: " + message;
  }
}