package br.ufrn.chat.client;


import br.ufrn.chat.config.ServerConfig;
import br.ufrn.chat.exception.ChatApiExceptionHandler;
import br.ufrn.dto.UserDTO;
import br.ufrn.requestbody.RegisterUserDTO;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente restful para os serviços de usuário.
 */
public class UserResourceClient{

    public UserDTO register(RegisterUserDTO registerUserDTO){
        RestTemplate restTemplate = new RestTemplate();

        try{
            return restTemplate.postForObject(String.format(ServerConfig.REGISTER_USER_URL, ServerConfig.SERVER_BASE_URL), registerUserDTO,UserDTO.class);
        }catch (HttpStatusCodeException ex){
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }

    }



}
