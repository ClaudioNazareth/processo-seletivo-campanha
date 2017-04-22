package br.com.campanha.api.rest;

import br.com.campanha.api.domain.CampanhaResource;
import br.com.campanha.domain.Campanha;
import br.com.campanha.fixture.CampanhaGenerator;
import br.com.campanha.repository.CampanhaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.tuple;


/**
 * Teste para validar  a API Rest de campanha
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampanhaControllerTest {

    @Autowired
    private CampanhaController campanhaController;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Before
    public void setUp() throws Exception {
        campanhaRepository.deleteAll();
        campanhaRepository.save(CampanhaGenerator.getCampanhas());
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    public void cadastrarCampanhaTest() throws Exception {
        ResponseEntity<?> responseEntity = campanhaController.cadastrarCampanha(CampanhaGenerator.criarCampanhaVigencia10Resource());
        assertThat(responseEntity).as("A campanha deve ser criada com sucesso").isNotNull();
        assertThat(responseEntity.getStatusCode()).as("O Status code deve ser created").isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().get("location")).as("Deve retornar a URL da campanha criada").isNotNull();
    }

    @Test
    public void naoDeveRetornarNadaQuandoNaoHouverCampanhasAtivas() throws Exception {
        ResponseEntity<List<CampanhaResource>> campanhas = campanhaController.buscarTodasCampanhas();
        assertThat(campanhas.getBody()).as("Não deve retornar nenhuma campanha, dado que não existem campanhas ativas").isEmpty();
    }

    @Test
    public void deveRetornarQuandoHouverCampanhasAtivas() throws Exception {
        campanhaRepository.save(CampanhaGenerator.getCampanhasAtivas());
        ResponseEntity<List<CampanhaResource>> campanhas = campanhaController.buscarTodasCampanhas();
        assertThat(campanhas.getBody()).as("Deve retornar as 2 campanhas ativas").hasSize(2)
                .extracting("nome", "timeCoracaoId", "inicioVigencia", "fimVigencia")
                .contains(tuple("Campanha 10", "TIME-1001", LocalDate.now(), LocalDate.now().plusDays(3)),
                        tuple("Campanha 20", "TIME-1001", LocalDate.now(), LocalDate.now().plusDays(5)));
    }


    @Test
    public void buscarPorId() throws Exception {
        Campanha campanha = campanhaRepository.findAll().stream().findAny().get();
        final ResponseEntity<CampanhaResource> responseEntity = campanhaController.buscarPorId(campanha.getId());
        assertThat(responseEntity.getStatusCode()).as("O Status code deve ser OK").isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody()).as("Deve retornar a Campanha e todos os seus dados")
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    public void deletarPorId() throws Exception {
        Campanha campanha = campanhaRepository.findAll().stream().findAny().get();
        final ResponseEntity<?> responseEntity = campanhaController.deletarPorId(campanha.getId());
        assertThat(responseEntity.getStatusCode()).as("O Status code deve ser NO_CONTENT").isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(campanhaRepository.findOne(campanha.getId())).as("A campanha tem que ser deletada").isNull();
    }

    @Test
    public void atualizarCampanha() throws Exception {
        Campanha campanha = campanhaRepository.findAll().stream().findAny().get();
        final ResponseEntity<?> responseEntity = campanhaController.atualizarCampanha(campanha.getId(), CampanhaGenerator.criarCampanhaVigencia10Resource());
        assertThat(responseEntity.getStatusCode()).as("O Status code deve ser NO_CONTENT").isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(campanhaRepository.findOne(campanha.getId()))
                .as("Os dados da campanha devem ser atualizados")
                .extracting("nome", "timeCoracaoId", "inicioVigencia", "fimVigencia")
                .contains("Campanha 4", "TIME-1004", LocalDate.of(2017,10,10),LocalDate.of(2017,10,20));
    }


    @Test
    public void handleValidationException() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> { campanhaController.cadastrarCampanha(CampanhaGenerator.criarCampanhaComFalhasResource()); })
                .withNoCause();
    }

}