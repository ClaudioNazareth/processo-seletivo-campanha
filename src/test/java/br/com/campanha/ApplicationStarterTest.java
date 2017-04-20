package br.com.campanha;

import br.com.campanha.repository.CampanhaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


/**
 * Testes referente as configurações inicialização da aplicação
 *
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationStarterTest {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Test
    public void quandoAplicacaoInicializarDadosDoArquivoJsonDevemSerCarregadosParaOMongoDB() throws Exception {
        assertThat(campanhaRepository.findAll())
                .as("Quando a aplicação e inicializada devem ser carregados dados do arquivo data.json para " +
                               "o MongoDB em memória")
                .isNotNull()
                .isNotEmpty()
                .as("Devem haver dois(2) registros no MongoDB")
                .hasSize(2)
                .as("Todos os campos devem ser preenchidos e estarem com os dados corretos")
                .extracting("nome", "timeCoracaoId", "inicioVigencia", "fimVigencia")
                .contains(tuple("Campanha 1", "TIME-1001", LocalDate.of(2017,10,01), LocalDate.of(2017,10,03) ),
                        tuple("Campanha 2", "TIME-1002", LocalDate.of(2017,10,01), LocalDate.of(2017,10,02)));
    }

}