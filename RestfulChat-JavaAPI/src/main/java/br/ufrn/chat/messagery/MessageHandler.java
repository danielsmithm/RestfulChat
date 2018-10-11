package br.ufrn.chat.messagery;

import br.ufrn.dto.MessageDTO;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface padrão para um handler de mensagens do lado do cliente.
 * <br><br>
 *     O handler de mensagens é o objeto capaz de tratar as notificações assíncronas de mensagens enviadas pelo servidor.
 */
public interface MessageHandler extends Remote, Serializable {
    /**
     * Retorna o usuário asssociado ao handler.
     * @return
     * @throws RemoteException
     */
    String getUserName();

    /**
     * Recebe a mensagem notificada pelo servidor.
     *
     * @param message
     * @throws RemoteException
     */
    void notifyMessage(MessageDTO message);
}
