package br.ufrn.resources;

import br.ufrn.dto.GroupDTO;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.requestbody.GroupCreationDTO;
import br.ufrn.requestbody.JoinGroupDTO;
import br.ufrn.requestbody.QuitGroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface para os resources do grupo.
 */
public interface GroupResources {

    void createGroup(@RequestBody GroupCreationDTO groupCreationDTO) throws UserNotFoundException;

    ResponseEntity<GroupDTO> findGroupById(@RequestParam String groupId) throws GroupNotExistsException;

    void joinGroup(@RequestBody JoinGroupDTO joinGroupDTO) throws GroupNotExistsException, UserNotFoundException;

    void quitGroup(@RequestBody QuitGroupDTO quitGroupDTO) throws GroupNotExistsException, UserNotFoundException;

    List<GroupDTO> listGroups(@RequestParam String username) throws GroupNotExistsException, UserNotFoundException;

    List<GroupDTO> listGroups() throws GroupNotExistsException;

}
