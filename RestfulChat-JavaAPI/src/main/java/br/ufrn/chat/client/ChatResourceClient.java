package br.ufrn.chat.client;

import br.ufrn.chat.messagery.MessageHandler;
import br.ufrn.chat.config.ServerConfig;
import br.ufrn.chat.utils.DTOAssembler;
import br.ufrn.dto.MessageDTO;
import br.ufrn.requestbody.SendMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatResourceClient {

    public void registerMessageHandler(MessageHandler messageHandler) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.execute(ServerConfig.SERVER_BASE_URL+"/chat/registernotification/?username="+messageHandler.getUserName(), HttpMethod.GET, request -> {
        }, response -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody()));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if(!line.isEmpty()){
                        //TODO:Remover débito técnico hehe
                        MessageDTO message = DTOAssembler.assemble(line.replace("data:",""),MessageDTO.class);

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

    public void unregisterMessageHandler(MessageHandler messageHandler){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getForObject(ServerConfig.SERVER_BASE_URL+"/chat/registernotification/?username="+messageHandler.getUserName(), String.class);
    }

    public void sendMessageToGroup(String groupId, String username, String messageContent) {

        SendMessageDTO sendMessageDTO = new SendMessageDTO();

        sendMessageDTO.setGroupId(groupId);
        sendMessageDTO.setUsername(username);
        sendMessageDTO.setMessageContent(messageContent);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(ServerConfig.SERVER_BASE_URL+"/chat/send",sendMessageDTO,String.class);

    }
}

