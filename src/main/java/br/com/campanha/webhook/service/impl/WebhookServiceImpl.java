package br.com.campanha.webhook.service.impl;

import br.com.campanha.api.domain.CampanhaResource;
import br.com.campanha.domain.Campanha;
import br.com.campanha.webhook.domain.Webhook;
import br.com.campanha.webhook.repository.WebhookRepository;
import br.com.campanha.webhook.service.NotificadorService;
import br.com.campanha.webhook.service.WebhookService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço para funcionalidades relacionadas a Webhook
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@Service
public class WebhookServiceImpl implements WebhookService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebhookServiceImpl.class);

    @Autowired
    private WebhookRepository webhookRepository;

    public Webhook cadastrarWebhook(String url, String chaveAcesso){
        return webhookRepository.save(new Webhook(url,chaveAcesso));
    }

    @HystrixCommand(fallbackMethod = "notificarFallback")
    public void notificarAtualizacao(Campanha campanha){

        webhookRepository.findAll().forEach(webhook -> {
            Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logger(new Slf4jLogger(NotificadorService.class))
                    .logLevel(Logger.Level.FULL)
                    .target(NotificadorService.class, webhook.getUrl())
                    .notifica(new CampanhaResource(campanha));
        });
    }

    public void notificarFallback(Campanha campanha){
        logger.debug("Este método não foi implementado!!!!");
        logger.debug("Armazenar esta campanha e tentar notificar novamente");
        System.out.println("Armazenar esta campanha e tentar notificar novamente");
    }
}
