package br.ufrn.chat;

import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.chat.messagery.MessageHandler;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.UserDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatAPI extends Remote, Serializable {

    /**
     * Registra um messageHandler para permitir a notificação assíncrona de mensagens para esse usuário.
     *
     * @param handler
     * @throws RemoteException
     */
    MessageHandler registerMessageHandler(MessageHandler handler) throws ChatCommunicationException;

    /**
     * Registra o usuário no sistema.
     *
     * @param username
     * @return
     * @throws RemoteException
     * @throws UserAlreadyExistsException
     */
    UserDTO register(String username) throws ChatCommunicationException, UserAlreadyExistsException;

    /**
     * Envia uma mensagem para um grupo.
     *
     * @param groupId
     * @param username
     * @param messageContent
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void sendMessageToGroup(String groupId, String username, String messageContent) throws ChatCommunicationException, GroupNotExistsException;

    /**
     * Cria um grupo.
     *
     * @param name
     * @param creator
     * @throws RemoteException
     */
    void createGroup(String name, UserDTO creator) throws ChatCommunicationException, UserNotFoundException;

    /**
     * Busca um grupo pelo seu id.
     *
     * @param groupId
     * @return
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    GroupDTO findGroupById(String groupId) throws ChatCommunicationException, GroupNotExistsException;

    /**
     * Associa um usuário ao grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void joinGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException;

    /**
     * Retira um usuário de um grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void quitGroup(String groupId, UserDTO user) throws ChatCommunicationException, GroupNotExistsException, UserNotFoundException;

    /**
     * Lista todos os grupos existentes.
     *
     * @return
     * @throws RemoteException
     */
    List<GroupDTO> listGroups() throws ChatCommunicationException;

    /**
     * Lista os grupos que o usuário está associado.
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    List<GroupDTO> listGroups(String username) throws ChatCommunicationException;
}
