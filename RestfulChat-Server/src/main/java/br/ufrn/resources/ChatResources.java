package br.ufrn.resources;

import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.requestbody.SendMessageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    SseEmitter registerEmmiter(String username);

    void unregisterUser(String username);
}
