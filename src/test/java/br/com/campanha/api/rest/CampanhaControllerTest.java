package br.com.campanha.api.rest;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


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
    public void cadastrarCampanha() throws Exception {
        ResponseEntity<?> responseEntity = campanhaController.cadastrarCampanha(CampanhaGenerator.criarCampanhaVigencia10Resource());
        assertThat(responseEntity).as("A campanha deve ser criada com sucesso").isNotNull();
        assertThat(responseEntity.getStatusCode()).as("O Status code deve ser created").isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().get("location")).as("Deve retornar a URL da campanha criada").isNotNull();
    }

    @Test
    public void handleInternalServerError() throws Exception {
    }

    @Test
    public void handleHttpMessageNotReadableException() throws Exception {
    }

    @Test
    public void handleValidationException() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> { campanhaController.cadastrarCampanha(CampanhaGenerator.criarCampanhaComFalhasResource()); })
                .withNoCause();
    }

}