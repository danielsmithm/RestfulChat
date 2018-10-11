package br.ufrn.chat.factory;

import br.ufrn.chat.ChatAPI;
import br.ufrn.chat.client.ChatResourceClient;
import br.ufrn.chat.client.GroupResourceClient;
import br.ufrn.chat.client.UserResourceClient;
import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.chat.messagery.EventBasedMessageHandlerDecorator;
import br.ufrn.chat.messagery.MessageHandler;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.UserDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.requestbody.GroupCreationDTO;
import br.ufrn.requestbody.JoinGroupDTO;
import br.ufrn.requestbody.QuitGroupDTO;
import br.ufrn.requestbody.RegisterUserDTO;

import java.util.List;

public class DefaultChatAPI implements ChatAPI {

    private ChatResourceClient chatResourceClient;
    private GroupResourceClient groupResourceClient;
    private UserResourceClient userResourceClient;

    public DefaultChatAPI(ChatResourceClient chatResourceClient, GroupResourceClient groupResourceClient, UserResourceClient userResourceClient) {
        this.chatResourceClient = chatResourceClient;
        this.groupResourceClient = groupResourceClient;
        this.userResourceClient = userResourceClient;
    }

    @Override
    public MessageHandler registerMessageHandler(MessageHandler handler){
        EventBasedMessageHandlerDecorator messageHandlerDecorator = new EventBasedMessageHandlerDecorator(handler,chatResourceClient);

        messageHandlerDecorator.startListeningEvents();

        return messageHandlerDecorator;
    }

    @Override
    public UserDTO register(String username) throws UserAlreadyExistsException, ChatCommunicationException {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername(username);

        return userResourceClient.register(registerUserDTO);
    }

    @Override
    public void sendMessageToGroup(String groupId, String username, String messageContent) throws ChatCommunicationException, GroupNotExistsException {
        chatResourceClient.sendMessageToGroup(groupId,username,messageContent);
    }

    @Override
    public void createGroup(String name, UserDTO creator) throws ChatCommunicationException, UserNotFoundException {
        GroupCreationDTO groupCreationDTO = new GroupCreationDTO();

        groupCreationDTO.setCreatorUsername(creator.getUserName());
        groupCreationDTO.setName(name);

        groupResourceClient.createGroup(groupCreationDTO);
    }

    @Override
    public GroupDTO findGroupById(String groupId) throws ChatCommunicationException, GroupNotExistsException {
        return groupResourceClient.findGroupById(groupId);
    }

    @Override
    public void joinGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException {
        JoinGroupDTO joinGroupDTO = new JoinGroupDTO();

        joinGroupDTO.setGroupId(groupId);
        joinGroupDTO.setUserName(user.getUserName());

        groupResourceClient.joinGroup(joinGroupDTO);
    }

    @Override
    public void quitGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException {

        QuitGroupDTO quitGroupDTO = new QuitGroupDTO();

        quitGroupDTO.setGroupId(groupId);
        quitGroupDTO.setUserName(user.getUserName());

        groupResourceClient.quitGroup(quitGroupDTO);

    }

    @Override
    public List<GroupDTO> listGroups() throws ChatCommunicationException {
        return groupResourceClient.listGroups();
    }

    @Override
    public List<GroupDTO> listGroups(String username) throws ChatCommunicationException {
        return groupResourceClient.listGroups(username);
    }

}
