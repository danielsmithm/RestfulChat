package br.ufrn.resources.implementation;

import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.resources.ChatResources;
import br.ufrn.requestbody.SendMessageDTO;
import br.ufrn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Implementação dos serviços Restful para o servidor de chat.
 */
@RequestMapping("/chat")
@RestController
public class ChatResourcesImpl implements ChatResources {

    private final EmitterStore emitterStore;
    private final GroupService groupService;

    @Autowired
    public ChatResourcesImpl(GroupService groupService, EmitterStore emmiterStore) {
        this.groupService = Objects.requireNonNull(groupService,"The group service is a mandatory dependency.");
        this.emitterStore = emmiterStore;
    }

    @RequestMapping(value = "/send",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessageToGroup(@RequestBody SendMessageDTO sendMessageDTO) throws GroupNotExistsException {
        groupService.sendMessageToGroup(sendMessageDTO.getGroupId(),sendMessageDTO.getUsername(),sendMessageDTO.getMessageContent());
    }

    @RequestMapping(value = "/registernotification")
    public SseEmitter registerEmmiter(@RequestParam String username){
        return emitterStore.createEmitterForUser(username);
    }

    @RequestMapping(value = "/unregisternotification")
    public void unregisterUser(@RequestParam String username){
       emitterStore.unregisterUser(username);
    }

    //EXCEPTION HANDLING
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({GroupNotExistsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForForbiddenException(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.FORBIDDEN.value(), e.getMessage(),e.getClass().getName()), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForResourceNotFoundExceptions(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(),e.getClass().getName()), HttpStatus.NOT_FOUND);
    }
}
