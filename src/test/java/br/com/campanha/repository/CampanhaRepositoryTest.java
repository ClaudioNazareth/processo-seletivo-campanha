package br.com.campanha.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Teste para validar se o Repositório esta trazendo os dados corretamente
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampanhaRepositoryTest {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Test
    public void deveTrazeApenasCampanhasValidas() throws Exception {

        assertThat(campanhaRepository.buscaTodasAsCampanhasAtivas(LocalDate.of(2017, 9, 02)))
                .as("Nenhuma campanha deve ser retornada pois nao existe Campanhas ativas(Data de inicio de vegencia e maior que data do parametro)")
                .isNullOrEmpty();

        assertThat(campanhaRepository.buscaTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 05)))
                .as("Nenhuma campanha deve ser retornada pois todas estao vencidas(Data de fim vigencia e menor que a data do parametro)")
                .isNullOrEmpty();

        assertThat(campanhaRepository.buscaTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 03)))
                .as("Deve Trazer somente uma campanha que esta ativa")
                .hasSize(1);

        assertThat(campanhaRepository.buscaTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 02)))
                .as("Deve Trazer as duas(2) campanhas que esta ativas")
                .hasSize(2);

    }

}