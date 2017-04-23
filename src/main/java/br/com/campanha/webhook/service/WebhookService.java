package br.com.campanha.webhook.service;

import br.com.campanha.domain.Campanha;
import br.com.campanha.webhook.domain.Webhook;
import org.springframework.stereotype.Service;

/**
 * Serviço para funcionalidades relacionadas a Webhook
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@Service
public interface WebhookService {
    Webhook cadastrarWebhook(String url, String chaveAcesso);
    void notificarAtualizacao(Campanha campanha);
}
