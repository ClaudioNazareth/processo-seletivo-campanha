package br.com.campanha.webhook.repository;

import br.com.campanha.webhook.domain.Webhook;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Contrato com as operações de persistência sob o document Webhook
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
public interface WebhookRepository  extends MongoRepository<Webhook, String> {
}
