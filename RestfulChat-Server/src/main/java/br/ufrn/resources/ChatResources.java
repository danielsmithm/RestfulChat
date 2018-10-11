package br.ufrn.resources;

import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.requestbody.SendMessageDTO;

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
