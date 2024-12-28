# **Documentação da API - Kafka GZIP Consumer**

## **Descrição do Projeto**
Esta API foi desenvolvida para consumir mensagens do Apache Kafka, salvá-las em arquivos no formato **GZIP** no disco e persistir os metadados em um banco de dados **PostgreSQL**. A aplicação utiliza o framework **Spring Boot** com Java 17 e possui uma rota exposta para recebimento de mensagens, permitindo a produção direta para o tópico Kafka.

---

## **Tecnologias Utilizadas**

- **Java 17**
- **Spring Boot**
- **Apache Kafka** (Serviço de Mensageria)
- **PostgreSQL** (Banco de Dados Relacional)
- **Docker** (Para execução local de Kafka)
- **GZIP** (Compressão das mensagens em arquivos)

---

## **Pré-requisitos**

1. **Java 17** instalado
2. **Docker** instalado e configurado
3. Servidor **PostgreSQL** configurado
4. Serviço do **Kafka** em execução (usando Docker)

---

## **Configuração do Projeto**

### **application.properties**

```properties
# Configurações da Aplicação
spring.application.name=kafkagzipconsumer 

# Configurações do Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=kafka-gzip-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
kafka.topic=my-topic

# Configurações para o GZIP
gzip.output-dir=./output
gzip.max-messages=5
gzip.time-interval=10000

# Configurações do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/kafkagzip?ssl=false
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

---

## **Execução do Projeto**

1. **Subir o Kafka utilizando Docker**:
   ```bash
   docker-compose up -d
   ```

2. **Produzir Mensagens no Kafka**:
   Execute o comando abaixo para produzir mensagens diretamente no tópico **my-topic**:
   ```bash
   docker exec -it broker kafka-console-producer.sh --broker-list localhost:9092 --topic my-topic
   ```
   ## OU
      
   ### Usar o **Frontend (Interface de Produção de Mensagens)**
   <br>
   
   
   👉🏾 **[Repository](https://github.com/jrrodrigo421/kafka-producer-test)**&nbsp;&nbsp;<img src="images_readme/github.png" alt="emoji" width="20" height="20">
   
   🛜 **[Página Online](https://kafkaproducertest.vercel.app)**
   
   <img src="images_readme/kafka_producer_ui.jpg" alt="emoji" width="320" height="380">

   1. **Clonar o Repositório Frontend**:
      ```bash
      git clone https://github.com/jrrodrigo421/kafka-producer-test.git
      cd kafka-producer-ui
      ```

   2. **Instalar Dependências**:
      ```bash
      npm install
      ```

   3. **Executar a Interface**:
      ```bash
      npm start
      ```
      - A interface será disponibilizada em `http://localhost:3000`.

   ---



3. **Executar a API**:
   - Execute o projeto Spring Boot com o seguinte comando:
     ```bash
     mvn spring-boot:run
     ```

---

## **Rotas da API**

### **1. Rota de Produção de Mensagens**

- **Endpoint**: `POST /api/kafka/send`
- **Descrição**: Permite enviar mensagens diretamente para o tópico Kafka **my-topic**.
- **Parâmetros**: 
   - **Body**: Texto simples (String)
- **Exemplo de Request**:
   ```http
   POST http://localhost:8080/api/kafka/send
   Content-Type: text/plain
   
   Esta é uma mensagem de teste para o Kafka!
   ```

---

## **Comandos Úteis**

1. **Verificar Tópicos do Kafka**:
   ```bash
   docker exec -it broker kafka-topics.sh --bootstrap-server localhost:9092 --list
   ```

2. **Consumir Mensagens**:
   ```bash
   docker exec -it broker kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-topic --from-beginning
   ```

---

## **Conclusão**
Essa documentação descreve como configurar, executar e interagir com a API Kafka GZIP Consumer, fornecendo um fluxo robusto e eficiente para consumo e persistência de mensagens em Kafka.

🚀
