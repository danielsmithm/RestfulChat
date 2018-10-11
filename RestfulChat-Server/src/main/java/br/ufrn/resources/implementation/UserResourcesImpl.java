package br.ufrn.resources.implementation;

import br.ufrn.domain.User;
import br.ufrn.dto.UserDTO;
import br.ufrn.dto.assembler.UserDTOAssembler;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.requestbody.RegisterUserDTO;
import br.ufrn.resources.configuration.Routes;
import br.ufrn.resources.UserResource;
import br.ufrn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;

@RestController
@RequestMapping(Routes.USER_RESOURCES)
public class UserResourcesImpl implements UserResource {

    private UserService userService;

    @Autowired
    public UserResourcesImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO register(@RequestBody RegisterUserDTO registerUserDTO) throws UserAlreadyExistsException {
        User registeredUser = userService.register(registerUserDTO.getUsername());

        return UserDTOAssembler.fromUser(registeredUser);
    }
}
