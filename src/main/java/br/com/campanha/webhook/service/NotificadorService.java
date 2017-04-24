package br.com.campanha.webhook.service;

import br.com.campanha.api.domain.CampanhaResource;
import feign.Headers;
import feign.RequestLine;

/**
 * Interface do serviço que irá notificar as APIs sobre atualizações em campanhas
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
public interface NotificadorService {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void notifica(CampanhaResource campanhaResource);
}
