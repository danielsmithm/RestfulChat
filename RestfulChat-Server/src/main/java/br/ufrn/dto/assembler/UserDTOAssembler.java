package br.ufrn.dto.assembler;

import br.ufrn.domain.User;
import br.ufrn.dto.UserDTO;

public class UserDTOAssembler {

    public static UserDTO fromUser(User user){
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();

        dto.setUserName(user.getUserName());

        return dto;
    }

}
