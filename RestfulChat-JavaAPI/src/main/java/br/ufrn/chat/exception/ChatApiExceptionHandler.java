package br.ufrn.chat.exception;

import br.ufrn.chat.utils.DTOAssembler;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ChatApiExceptionHandler {

    public static Exception getExceptionHttpExceptionCause(HttpStatusCodeException ex){
        String responseBodyAsString = ex.getResponseBodyAsString();
        if (responseBodyAsString != null && ex.getStatusCode() == HttpStatus.NOT_FOUND || ex.getStatusCode() == HttpStatus.FORBIDDEN) {

            ServerErrorDTO assemble = DTOAssembler.assemble(responseBodyAsString, ServerErrorDTO.class);

            if (assemble.getExceptionClassName().equals(GroupNotExistsException.class.getName())) {
                return new GroupNotExistsException(assemble.getMessage());
            }

            if (assemble.getExceptionClassName().equals(UserAlreadyExistsException.class.getName())) {
                return new UserAlreadyExistsException(assemble.getMessage());
            }

            if (assemble.getExceptionClassName().equals(UserNotFoundException.class.getName())) {
                return new UserNotFoundException(assemble.getMessage());
            }

        }

        return new ChatCommunicationException("An error ocurred while communicating with the chat server.",ex);
    }

    public static void handleIfUserNotFoundException(Throwable cause) throws UserNotFoundException{
        if(cause instanceof UserNotFoundException){
            throw (UserNotFoundException) cause;
        }
    }

    public static void handleIfChatCommunicationException(Throwable cause) throws ChatCommunicationException{
        if(cause instanceof ChatCommunicationException){
            throw (ChatCommunicationException) cause;
        }
    }

    public static void handleIfUserAlreadyExistsException(Throwable cause) throws UserAlreadyExistsException {
        if(cause instanceof UserAlreadyExistsException){
            throw (UserAlreadyExistsException) cause;
        }
    }

    public static void handleIfGroupNotExistsException(Throwable cause) throws GroupNotExistsException {
        if(cause instanceof GroupNotExistsException){
            throw (GroupNotExistsException) cause;
        }
    }
}
