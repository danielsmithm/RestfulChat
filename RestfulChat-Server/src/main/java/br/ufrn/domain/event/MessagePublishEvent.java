package br.ufrn.domain.event;

import br.ufrn.dto.MessageDTO;

import java.util.Set;

public class MessagePublishEvent {
    private MessageDTO messageDTO;
    private Set<String> usersToNotify;

    public MessagePublishEvent(MessageDTO messageDTO) {
        this.messageDTO = messageDTO;
    }

    public MessagePublishEvent(MessageDTO messageDTO, Set<String> usersToNotify) {
        this.usersToNotify = usersToNotify;
        this.messageDTO = messageDTO;
    }

    public MessageDTO getMessageDTO() {
        return messageDTO;
    }

    public Set<String> getUsersToNotify() {
        return usersToNotify;
    }
}
