package br.ufrn.service.implementation;

import br.ufrn.domain.event.MessagePublishEvent;
import br.ufrn.resources.implementation.EmitterStore;
import br.ufrn.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Implementação do publisher de mensagens.
 * <br>
 *
 * @see br.ufrn.service.MessagePublisher
 */
@Service
public class MessagePublisherImpl implements MessagePublisher {

    private final EmitterStore emitterStore;

    @Autowired
    public MessagePublisherImpl(EmitterStore emitterStore) {
        this.emitterStore = emitterStore;
    }

    @Async
    @Override
    public void publishMessageCreation(MessagePublishEvent event){
        emitterStore.registeredUsers()
                .parallelStream()
                .filter(username -> event.getUsersToNotify().contains(username))
                .forEach(username -> emitterStore.notifyUser(event.getMessageDTO(), username));
    }

}
