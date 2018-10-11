package br.ufrn.chat.factory;

import br.ufrn.chat.ChatAPI;
import br.ufrn.chat.client.ChatResourceClient;
import br.ufrn.chat.client.GroupResourceClient;
import br.ufrn.chat.client.UserResourceClient;

public class ChatAPIFactory {

    private static DefaultChatAPI chatApi;

    public static ChatAPI getChatApi(){

        if(chatApi == null){
            chatApi =  new DefaultChatAPI(new ChatResourceClient(), new GroupResourceClient(), new UserResourceClient());
        }

        return chatApi;
    }

}
