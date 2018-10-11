package br.ufrn.service.implementation;

import br.ufrn.domain.User;
import br.ufrn.exceptions.UserAlreadyExistsException;

import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.repository.UserRepository;
import br.ufrn.service.UserService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Serviço para cadastro de usuários.
 *
 * @see br.ufrn.service.UserService
 */
@Service
public class UserServiceImpl implements UserService,Serializable {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username) throws UserAlreadyExistsException {

        if(userRepository.existsUserName(username)){
            throw new UserAlreadyExistsException("Already exists an user with the request username");
        }

        User user = User.createUser(username);

        userRepository.add(user);

        return user;
    }

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username);
    }

}
