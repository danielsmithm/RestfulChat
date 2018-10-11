package br.ufrn.resources;

import br.ufrn.dto.UserDTO;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.requestbody.RegisterUserDTO;

import java.rmi.RemoteException;

public interface UserResource {
    /**
     * Registra o usuário no sistema.
     *
     *
     * @param registerUserDTO@return
     * @throws RemoteException
     * @throws UserAlreadyExistsException
     */
    UserDTO register(RegisterUserDTO registerUserDTO) throws RemoteException, UserAlreadyExistsException;
}
