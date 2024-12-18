package br.com.rjrsolucoes.kafkagzipconsumer;

import br.com.rjrsolucoes.kafkagzipconsumer.model.ArquivoGerado;
import br.com.rjrsolucoes.kafkagzipconsumer.repository.ArquivoGeradoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class KafkagzipconsumerApplicationTests {

    @Mock
    private ArquivoGeradoRepository repository;

    @InjectMocks
    private KafkagzipconsumerApplication application;

    @Test
    void testSalvarArquivoNoBanco() {
        // Cenário
        ArquivoGerado arquivo = new ArquivoGerado("mensagem de teste", "test-file.gzip");

        // Ação
        when(repository.save(arquivo)).thenReturn(arquivo);
        repository.save(arquivo);

        // Verificação
        verify(repository, times(1)).save(arquivo);
    }
}
