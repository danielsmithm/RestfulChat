package br.ufrn.chat.messagery;

import br.ufrn.chat.client.ChatResourceClient;
import br.ufrn.dto.MessageDTO;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventBasedMessageHandlerDecorator implements MessageHandler {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final MessageHandler delegate;
    private final ChatResourceClient chatResourceClient;

    public EventBasedMessageHandlerDecorator(MessageHandler delegate, ChatResourceClient resourceClient) {
        this.delegate = delegate;
        this.chatResourceClient = resourceClient;
        startListeningEvents();
    }

    public void startListeningEvents() {
        CompletableFuture.runAsync(() ->
            chatResourceClient.registerMessageHandler(this)
        );
    }

    @Override
    public String getUserName(){
        return delegate.getUserName();
    }

    @Override
    public void notifyMessage(MessageDTO message){
        try{
            delegate.notifyMessage(message);
        }catch (Exception ex){
            logger.log(Level.SEVERE,"Error on notifying message to client",ex);
            unregisterHandler();
        }
    }

    private void unregisterHandler() {
        chatResourceClient.unregisterMessageHandler(this);
    }
}
