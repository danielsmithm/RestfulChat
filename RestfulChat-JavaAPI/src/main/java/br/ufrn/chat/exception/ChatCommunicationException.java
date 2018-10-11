package br.ufrn.chat.exception;

/**
 * Exceção lançada na ocorrência de erros com o servidor de chat.
 */
public class ChatCommunicationException extends Exception {
    public ChatCommunicationException(String message) {
        super(message);
    }
}
