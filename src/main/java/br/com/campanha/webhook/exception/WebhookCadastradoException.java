package br.com.campanha.webhook.exception;

/**
 * Será retornada pelo API PUT quando já houver um webhook com o mesmo url cadastrado
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */
public class WebhookCadastradoException extends RuntimeException  {

    public WebhookCadastradoException() {
        super("Webhook(URL) já cadastrado");
    }
}
