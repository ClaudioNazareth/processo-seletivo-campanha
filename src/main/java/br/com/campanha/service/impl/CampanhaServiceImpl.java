package br.com.campanha.service.impl;

import br.com.campanha.domain.Campanha;
import br.com.campanha.repository.CampanhaRepository;
import br.com.campanha.service.CampanhaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para funcionalidades relacionadas a Campanha
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@Service
@Validated
@Transactional(rollbackFor=Exception.class)
public class CampanhaServiceImpl implements CampanhaService {

    private static final Logger logger = LoggerFactory.getLogger(CampanhaService.class);

    private  final int NUMERO_DIAS = 1;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Override
    public List<Campanha> buscarTodasAsCampanhasAtivas(LocalDate hoje) {
        return campanhaRepository.buscarTodasAsCampanhasAtivas(hoje);
    }

    @Override
    public List<Campanha> buscarCampanhasAtivasPorPeriodo(LocalDate inicioVigencia, LocalDate fimVigencia) {
        return campanhaRepository.buscarCampanhasAtivasPorPeriodo(inicioVigencia, fimVigencia);
    }

    @Override
    public Campanha cadastrarCampanha(String nomeDaCampanha, String idDoTimeCoracao, LocalDate inicioVigencia, LocalDate fimVigencia) {
        logger.debug("Cadastrando Campanha com nome {} - Data de Inicio Vigência: {}  - Data de Fim Vigência : {} ",
                nomeDaCampanha , inicioVigencia , fimVigencia);

        List<Campanha> campanhas = campanhaRepository.buscarCampanhasAtivasPorPeriodo(inicioVigencia, fimVigencia);
        Collections.sort(campanhas, Comparator.comparing(Campanha::getFimVigencia));

        campanhas.forEach(campanhaCadastrada -> {
            campanhaCadastrada.setFimVigencia(campanhaCadastrada.getFimVigencia().plusDays(NUMERO_DIAS));
            adicionaDiaAoFimVigenciaRecursivo(campanhaCadastrada, campanhas);
        } );

        campanhaRepository.save(campanhas);
        return campanhaRepository.save(new Campanha(nomeDaCampanha, idDoTimeCoracao, inicioVigencia, fimVigencia));
    }

    @Override
    public Optional<Campanha> buscarPorId(String id) {
        return Optional.ofNullable(campanhaRepository.findOne(id));
    }

    @Override
    public void deletarPorId(String id) {
        campanhaRepository.delete(id);
    }

    @Override
    public void salvarCampanha(Campanha campanha) {
        campanhaRepository.save(campanha);
    }

    /**
     *
     * @param campanha - Campanha que vai ser comparadas com as outras campaanhas para validar a necessidade de se adicionar mais um dia
     * @param campanhasCadastradas - Listas de campanhas cadastras com vigência dentro da vigência da nova campanha a ser cadastrada
     */
    private void adicionaDiaAoFimVigenciaRecursivo(Campanha campanha, List<Campanha> campanhasCadastradas){
        if(campanhasCadastradas.stream()
                .filter(campanhaCadastrada -> !campanhaCadastrada.equals(campanha))
                .anyMatch(campanhaCadastrada -> campanhaCadastrada.getFimVigencia().isEqual(campanha.getFimVigencia()))){

            logger.debug("Adcionando fim de vigência na Campanha: {}" , campanha);
            campanha.setFimVigencia(campanha.getFimVigencia().plusDays(NUMERO_DIAS));
            adicionaDiaAoFimVigenciaRecursivo(campanha, campanhasCadastradas);
        }
    }
}
