package br.ufrn.resources.implementation;

import br.ufrn.domain.User;
import br.ufrn.dto.UserDTO;
import br.ufrn.dto.assembler.UserDTOAssembler;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.requestbody.RegisterUserDTO;
import br.ufrn.resources.configuration.Routes;
import br.ufrn.resources.UserResource;
import br.ufrn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.rmi.RemoteException;

/**
 * Implementação dos recursos Restful das operações de usuário.
 */
@RestController
@RequestMapping(Routes.USER_RESOURCES)
public class UserResourcesImpl implements UserResource {

    private UserService userService;

    @Autowired
    public UserResourcesImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registra um usuário no servidor.
     *
     * @param registerUserDTO Dto contendo o body da requisição
     * @return
     * @throws UserAlreadyExistsException Caso o usuário já exista
     */
    @Override
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO register(@RequestBody RegisterUserDTO registerUserDTO) throws UserAlreadyExistsException {
        User registeredUser = userService.register(registerUserDTO.getUsername());

        return UserDTOAssembler.fromUser(registeredUser);
    }

    //EXCEPTION HANDLING
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({GroupNotExistsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForForbiddenException(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.FORBIDDEN.value(), e.getMessage(),e.getClass().getName()), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForResourceNotFoundExceptions(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage(),e.getClass().getName()), HttpStatus.NOT_FOUND);
    }
}
