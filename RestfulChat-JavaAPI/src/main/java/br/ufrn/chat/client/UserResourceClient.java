package br.ufrn.chat.client;


import br.ufrn.chat.config.ServerConfig;
import br.ufrn.chat.exception.ChatApiExceptionHandler;
import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.chat.utils.DTOAssembler;
import br.ufrn.dto.UserDTO;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.requestbody.RegisterUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class UserResourceClient{

    public UserDTO register(RegisterUserDTO registerUserDTO) throws ChatCommunicationException, UserAlreadyExistsException {
        RestTemplate restTemplate = new RestTemplate();

        try{
            return restTemplate.postForObject(ServerConfig.SERVER_BASE_URL+"/users"+"/register", registerUserDTO,UserDTO.class);
        }catch (HttpStatusCodeException ex){
            ChatApiExceptionHandler.handleException(ex);
        }

        return null;

    }



}
