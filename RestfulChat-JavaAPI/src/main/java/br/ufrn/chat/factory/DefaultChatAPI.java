package br.ufrn.chat.factory;

import br.ufrn.chat.ChatAPI;
import br.ufrn.chat.client.ChatResourceClient;
import br.ufrn.chat.client.ClientException;
import br.ufrn.chat.client.GroupResourceClient;
import br.ufrn.chat.client.UserResourceClient;
import br.ufrn.chat.exception.ChatApiExceptionHandler;
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

import static br.ufrn.chat.exception.ChatApiExceptionHandler.*;

/**
 * Implementação da api de chat.
 */
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
    public MessageHandler registerMessageHandler(MessageHandler handler) {
        EventBasedMessageHandlerDecorator messageHandlerDecorator = new EventBasedMessageHandlerDecorator(handler, chatResourceClient);

        messageHandlerDecorator.startListeningEvents();

        return messageHandlerDecorator;
    }

    @Override
    public UserDTO register(String username) throws UserAlreadyExistsException, ChatCommunicationException {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername(username);

        try {
            return userResourceClient.register(registerUserDTO);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfUserAlreadyExistsException(cause);
            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

    @Override
    public void sendMessageToGroup(String groupId, String username, String messageContent) throws ChatCommunicationException, GroupNotExistsException {
        try {
            chatResourceClient.sendMessageToGroup(groupId, username, messageContent);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfGroupNotExistsException(cause);
            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

    @Override
    public void createGroup(String name, UserDTO creator) throws ChatCommunicationException, UserNotFoundException {
        GroupCreationDTO groupCreationDTO = new GroupCreationDTO();

        groupCreationDTO.setCreatorUsername(creator.getUserName());
        groupCreationDTO.setName(name);
        try {
            groupResourceClient.createGroup(groupCreationDTO);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfUserNotFoundException(cause);
            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

    @Override
    public GroupDTO findGroupById(String groupId) throws ChatCommunicationException, GroupNotExistsException {
        try {
            return groupResourceClient.findGroupById(groupId);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfGroupNotExistsException(cause);
            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

    @Override
    public void joinGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException {
        JoinGroupDTO joinGroupDTO = new JoinGroupDTO();

        joinGroupDTO.setGroupId(groupId);
        joinGroupDTO.setUserName(user.getUserName());

        try {
            groupResourceClient.joinGroup(joinGroupDTO);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfUserNotFoundException(cause);
            throwIfChatCommunicationException(cause);
            throwIfGroupNotExistsException(cause);

            throw ex;
        }
    }

    @Override
    public void quitGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException {

        QuitGroupDTO quitGroupDTO = new QuitGroupDTO();

        quitGroupDTO.setGroupId(groupId);
        quitGroupDTO.setUserName(user.getUserName());

        try {
            groupResourceClient.quitGroup(quitGroupDTO);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfUserNotFoundException(cause);
            throwIfGroupNotExistsException(cause);
            throwIfChatCommunicationException(cause);

            throw ex;
        }

    }

    @Override
    public List<GroupDTO> listGroups() throws ChatCommunicationException {
        try {
            return groupResourceClient.listGroups();
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

    @Override
    public List<GroupDTO> listGroups(String username) throws ChatCommunicationException {
        try {
            return groupResourceClient.listGroups(username);
        } catch (ClientException ex) {
            Throwable cause = ex.getCause();

            throwIfChatCommunicationException(cause);

            throw ex;
        }
    }

}
