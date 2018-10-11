package br.ufrn.service;

import br.ufrn.domain.event.MessagePublishEvent;

/**
 * Interface para o message publisher.
 */
public interface MessagePublisher {

    /**
     *  Publica a criação da mensagem para os usuários passados.
     *  <br>
     *  A implementação deste método deverá efetuar o envio da notificação
     *  para os usuários a serem notificados de forma assíncrona,
     *  visto que o número de usuários a notificar pode ser alto e a notificação ser uma operação bloqueante.
     *
     * @param event
     */
    void publishMessageCreation(MessagePublishEvent event);
}
