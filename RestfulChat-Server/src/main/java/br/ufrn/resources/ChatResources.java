package br.ufrn.resources;

import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.requestbody.SendMessageDTO;

/**
 * Interface para os resources do chat.
 */
public interface ChatResources {
    /**
     * Envia uma mensagem para um grupo.
     *
     *
     * @param sendMessageDTO
     * @throws GroupNotExistsException
     */
    void sendMessageToGroup(SendMessageDTO sendMessageDTO) throws GroupNotExistsException;

}
