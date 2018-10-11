package br.ufrn.resources.implementation;

import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.resources.ChatResources;
import br.ufrn.requestbody.SendMessageDTO;
import br.ufrn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;

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
        groupService.sendMessageToGroup(sendMessageDTO.getGroupId(),sendMessageDTO.getUsername(),sendMessageDTO.getUsername());
    }

    @RequestMapping(value = "/registernotification")
    public SseEmitter registerEmmiter(@RequestParam String username){
        return emitterStore.createEmitterForUser(username);
    }

    @RequestMapping(value = "/unregisternotification")
    public void unregisterUser(@RequestParam String username){
       emitterStore.unregisterUser(username);
    }
}
