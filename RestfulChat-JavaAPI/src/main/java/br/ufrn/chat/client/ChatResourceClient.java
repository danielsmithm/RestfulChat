package br.ufrn.chat.client;

import br.ufrn.chat.exception.ChatApiExceptionHandler;
import br.ufrn.chat.messagery.MessageHandler;
import br.ufrn.chat.config.ServerConfig;
import br.ufrn.chat.utils.DTOAssembler;
import br.ufrn.dto.MessageDTO;
import br.ufrn.requestbody.SendMessageDTO;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Cliente restful para os serviços de chat
 */
public class ChatResourceClient {

    public void registerMessageHandler(MessageHandler messageHandler) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.execute(String.format(ServerConfig.REGISTER_NOTIFICATION_URL, ServerConfig.SERVER_BASE_URL, messageHandler.getUserName()), HttpMethod.GET, request -> {
        }, response -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody()));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        //TODO:Remover débito técnico hehe
                        MessageDTO message = DTOAssembler.assemble(line.replace("data:", ""), MessageDTO.class);

                        System.out.println(line);
                        messageHandler.notifyMessage(message);
                    }
                }
            } catch (Exception e) {
                //TODO:Remover débito técnico hehe
                System.out.println(e);
                throw new RuntimeException();
            }

            return response;

        });
    }

    public void unregisterMessageHandler(MessageHandler messageHandler) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(String.format(ServerConfig.UNREGISTER_NOTIFICATION_URL, ServerConfig.SERVER_BASE_URL, messageHandler.getUserName()), String.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }

    public void sendMessageToGroup(String groupId, String username, String messageContent) {

        SendMessageDTO sendMessageDTO = new SendMessageDTO();

        sendMessageDTO.setGroupId(groupId);
        sendMessageDTO.setUsername(username);
        sendMessageDTO.setMessageContent(messageContent);

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(String.format(ServerConfig.SEND_MESSAGE_URL, ServerConfig.SERVER_BASE_URL), sendMessageDTO, String.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }

    }
}

