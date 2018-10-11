package br.ufrn.chat.client;

public class ClientException extends RuntimeException{

    public ClientException(Exception cause) {
        super(cause);
    }
}
