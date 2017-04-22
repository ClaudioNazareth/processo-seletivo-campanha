package br.com.campanha.fixture;

import br.com.campanha.api.domain.CampanhaResource;
import br.com.campanha.domain.Campanha;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para criar dados de teste
 *
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
public class CampanhaGenerator {

    public static List<Campanha> getCampanhas(){
        List<Campanha> campanhas = new ArrayList();
        campanhas.add(new Campanha("Campanha 1",  "TIME-1001",
                LocalDate.of(2017,10,01),LocalDate.of(2017,10,03)));

        campanhas.add(new Campanha("Campanha 2",  "TIME-1002",
                LocalDate.of(2017,10,01),LocalDate.of(2017,10,02)));
        return campanhas;
    }

    public static List<Campanha> getCampanhasAtivas(){
        List<Campanha> campanhas = new ArrayList();
        campanhas.add(new Campanha("Campanha 10",  "TIME-1001",
                LocalDate.now(), LocalDate.now().plusDays(3)));

        campanhas.add(new Campanha("Campanha 20",  "TIME-1001",
                LocalDate.now(), LocalDate.now().plusDays(5)));
        return campanhas;
    }

    public static CampanhaResource criarCampanhaVigencia10Resource(){
        return new CampanhaResource("Campanha 4",  "TIME-1004",
                LocalDate.of(2017,10,10),LocalDate.of(2017,10,20));

    }

    public static CampanhaResource criarCampanhaComFalhasResource(){
        return new CampanhaResource(null,  "TIME-1004",
                LocalDate.of(2017,10,10),LocalDate.of(2017,10,20));

    }
}
