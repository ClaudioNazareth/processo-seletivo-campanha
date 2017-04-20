package br.com.campanha.repository;

import br.com.campanha.domain.Campanha;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Contrato com as operações de persistência sob o document Campanha
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */

@Repository
public interface CampanhaRepository extends MongoRepository<Campanha, String> {

    @Query("{ 'inicioVigencia' : { $lte: ?0}, 'fimVigencia' : {$gte: ?0} }")
    List<Campanha> buscaTodasAsCampanhasAtivas(LocalDate localDate);

}