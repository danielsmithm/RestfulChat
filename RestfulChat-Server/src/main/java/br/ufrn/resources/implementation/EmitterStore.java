package br.ufrn.resources.implementation;

import br.ufrn.domain.event.MessagePublishEvent;
import br.ufrn.dto.MessageDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EmitterStore {

    private Map<String, SseEmitter> registeredEmitters = new HashMap<String, SseEmitter>();

    public SseEmitter createEmitterForUser(@RequestParam String username) {
        SseEmitter emitter = new SseEmitter();

        emitter.onCompletion(() -> this.registeredEmitters.remove(emitter));
        emitter.onTimeout(() -> this.registeredEmitters.remove(emitter));

        this.registeredEmitters.put(username,emitter);

        return emitter;
    }


    public void notifyUser(Object message, String username){

        SseEmitter emitter = getEmitter(username);

        if(emitter != null) {
            try {
                emitter.send(message);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error on notifying user using SseEmitter", e);
            }
        }

    }

    public Set<String> registeredUsers(){
        return registeredEmitters.keySet();
    }

    public SseEmitter getEmitter(String username){
        return registeredEmitters.get(username);
    }

    public void unregisterUser(String username) {
        SseEmitter emitter = this.getEmitter(username);
        if(emitter != null){
            emitter.complete();
            registeredEmitters.remove(username);
        }
    }
}