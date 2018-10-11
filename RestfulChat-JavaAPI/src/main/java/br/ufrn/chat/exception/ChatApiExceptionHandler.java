package br.ufrn.chat.exception;

import br.ufrn.chat.utils.DTOAssembler;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class ChatApiExceptionHandler {

    public static void handleException(HttpStatusCodeException ex) throws ChatCommunicationException {
        String responseBodyAsString = ex.getResponseBodyAsString();
        if(responseBodyAsString != null && ex.getStatusCode() == HttpStatus.NOT_FOUND || ex.getStatusCode() == HttpStatus.FORBIDDEN){
            try {
                ServerErrorDTO assemble = DTOAssembler.assemble(responseBodyAsString, ServerErrorDTO.class);

                if (assemble.getExceptionClassName().equals(GroupNotExistsException.class.getName())) {
                    throw new GroupNotExistsException(assemble.getMessage());
                }

                if (assemble.getExceptionClassName().equals(UserAlreadyExistsException.class.getName())) {
                    throw new GroupNotExistsException(assemble.getMessage());
                }

                if (assemble.getExceptionClassName().equals(UserAlreadyExistsException.class.getName())) {
                    throw new UserAlreadyExistsException(assemble.getMessage());
                }

            }catch (Exception e1){
                throw ex;
            }
        }

        throw new ChatCommunicationException("An error ocurred while communicating with the chat server.");
    }
}
