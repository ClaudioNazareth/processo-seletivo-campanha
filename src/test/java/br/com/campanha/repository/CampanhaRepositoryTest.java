package br.com.campanha.repository;

import br.com.campanha.fixture.CampanhaGenerator;
import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
        campanhaRepository.deleteAll();
        campanhaRepository.save(CampanhaGenerator.getCampanhas());
    }

    /**
     * Testa o requisito 1 -  O Sistema não deverá retornar campanhas que estão com a data de vigência vencidas
     * @throws Exception
     */
    @Test
    public void deveTrazerCampanhasComDataDeFimVigenciaoSuperiorADataDoParametro() throws Exception {


        assertThat(campanhaRepository.buscarTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 03)))
                .as("Deve Trazer somente a Campanha 1 - que esta ativa - Data fim vigência em 03/10/2017")
                .hasSize(1);

        assertThat(campanhaRepository.buscarTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 02)))
                .as("Deve Trazer as duas(2) Campanhas (1 e 2) que tem datas de fim vigÊncia inferior ao parâmetro")
                .hasSize(2);
    }

    /**
     * Testa o requisito 1 -  O Sistema não deverá retornar campanhas que estão com a data de vigência vencidas
     * @throws Exception
     */
    @Test
    public void naoDeveTrazerCampanhasComDataDeFimVigenciaoInferiorADataDoParametro() throws Exception {

        assertThat(campanhaRepository.buscarTodasAsCampanhasAtivas(LocalDate.of(2017, 9, 02)))
                .as("Nenhuma campanha deve ser retornada pois não existem Campanhas ativas (Data de inicio de " +
                        "vegência é maior que data do parâmetro)")
                .isNullOrEmpty();


        assertThat(campanhaRepository.buscarTodasAsCampanhasAtivas(LocalDate.of(2017, 10, 05)))
                .as("Nenhuma campanha deve ser retornada pois todas as campanhas cadastradas no sistema estao " +
                        "vencidas (Data de fim vigência é menor que a data do parametro)")
                .isNullOrEmpty();
    }

    /**
     * Faz parte o requisito 2 - trazer as campanhas ativas no período
     * @throws Exception
     */
    @Test
    public void deveTrazerCampanhasAtivasPorPeriodo() throws Exception {


        assertThat(campanhaRepository.buscarCampanhasAtivasPorPeriodo(
                LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 02)))
                .as("Deve trazersomente uma Campanha (Campanha 2) dado que somente ela esta dentro da vigência deste período")
                .hasSize(1);

        assertThat(campanhaRepository.buscarCampanhasAtivasPorPeriodo(
                LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 03)))
                .as("Deve trazer as duas Campanhas ativas dado que todas estão com a vigência dentro deste período")
                .hasSize(2);

        assertThat(campanhaRepository.buscarCampanhasAtivasPorPeriodo(
                LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 04)))
                .as("Deve trazer as duas Campanhas ativas dado que todas estão com a vigência dentro deste período")
                .hasSize(2);
    }

    /**
     * Faz parte o requisito 2 - trazer as campanhas ativas no período
     * @throws Exception
     */
    @Test
    public void naoDeveTrazerCampanhasQuandoPeriodoEstiverForaDosParametros() throws Exception {

        assertThat(campanhaRepository.buscarCampanhasAtivasPorPeriodo(
                LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 01)))
                .as("Não existe nenhuma campanha vigênte neste período")
                .isEmpty();


        assertThat(campanhaRepository.buscarCampanhasAtivasPorPeriodo(
                LocalDate.of(2017, 10, 02), LocalDate.of(2017, 10, 04)))
                .as("Não existe nenhuma campanha vigênte neste período")
                .isEmpty();
    }
}