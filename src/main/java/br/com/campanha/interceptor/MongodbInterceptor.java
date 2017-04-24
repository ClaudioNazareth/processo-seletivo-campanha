package br.com.campanha.interceptor;

import br.com.campanha.domain.Campanha;
import br.com.campanha.webhook.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Service;

/**
 * Interceptor para as operações de persistência sob o document Campanha
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
@Service
public class MongodbInterceptor extends AbstractMongoEventListener<Campanha> {

    @Autowired
    private WebhookService webhookService;

    /**
     * Após atualização ou cadastro de uma nova campanha, a aplicação notifica todas as apis cadastradas no webhook.
     * @param event
     */
    @Override
    public void onAfterSave(AfterSaveEvent<Campanha> event) {
        super.onAfterSave(event);
        webhookService.notificarAtualizacao(event.getSource());
    }
}
