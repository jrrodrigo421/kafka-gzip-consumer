package br.com.rjrsolucoes.kafkagzipconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import br.com.rjrsolucoes.kafkagzipconsumer.model.ArquivoGerado;
import br.com.rjrsolucoes.kafkagzipconsumer.repository.ArquivoGeradoRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.GZIPOutputStream;

@SpringBootApplication
public class KafkagzipconsumerApplication {

	@Autowired
	private ArquivoGeradoRepository repository;

	@Autowired

	private static final Logger logger = LoggerFactory.getLogger(KafkagzipconsumerApplication.class);

	@Value("${gzip.output-dir}")
	private String outputDir;

	@Value("${gzip.max-messages}")
	private int maxMessages;

	@Value("${gzip.time-interval}")
	private long timeInterval;

	@Value("${kafka.topic}")
	private String kafkaTopic;

	private final BlockingQueue<String> messageBuffer = new LinkedBlockingQueue<>();
	private long lastDumpTime = System.currentTimeMillis();

	public static void main(String[] args) {
		SpringApplication.run(KafkagzipconsumerApplication.class, args);
	}

	@KafkaListener(topics = "${kafka.topic}", groupId = "kafka-gzip-group")
	public void listen(ConsumerRecord<String, String> record) {
		try {
			messageBuffer.put(record.value());
			if (shouldDump()) {
				dumpMessagesToDisk();
			}
		} catch (InterruptedException e) {
			logger.error("Erro ao adicionar mensagem ao buffer", e);
			Thread.currentThread().interrupt();
		}
	}

	private boolean shouldDump() {
		return messageBuffer.size() >= maxMessages ||
				(System.currentTimeMillis() - lastDumpTime) >= timeInterval;
	}

	private void dumpMessagesToDisk() {
		if (messageBuffer.isEmpty())
			return;

		StringBuilder mensagens = new StringBuilder();

		try {
			Path outputDirPath = Paths.get(outputDir);
			if (!Files.exists(outputDirPath)) {
				Files.createDirectories(outputDirPath);
			}

			String fileName = "messages-" + System.currentTimeMillis() + ".gzip";
			Path outputPath = outputDirPath.resolve(fileName);

			try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(outputPath.toFile()))) {
				while (!messageBuffer.isEmpty()) {
					String message = messageBuffer.take();
					mensagens.append(message).append("\n");
					gzipOutputStream.write((message + "\n").getBytes());
				}
			}

			ArquivoGerado arquivo = new ArquivoGerado(mensagens.toString(), fileName);
			repository.save(arquivo);

			logger.info("Mensagens despejadas em: {}", outputPath);
		} catch (IOException | InterruptedException e) {
			logger.error("Erro ao gravar mensagens no disco", e);
			Thread.currentThread().interrupt();
		} finally {
			lastDumpTime = System.currentTimeMillis();
		}
	}

}
