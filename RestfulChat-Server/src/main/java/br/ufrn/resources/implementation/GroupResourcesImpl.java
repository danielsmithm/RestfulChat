package br.ufrn.resources.implementation;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.assembler.GroupDTOAssembler;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.requestbody.JoinGroupDTO;
import br.ufrn.requestbody.QuitGroupDTO;
import br.ufrn.resources.configuration.Routes;
import br.ufrn.exceptionhandling.ServerErrorDTO;
import br.ufrn.requestbody.GroupCreationDTO;
import br.ufrn.service.GroupService;
import br.ufrn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(Routes.GROUP_RESOURCES)
public class GroupResourcesImpl {

    private GroupService groupService;
    private UserService userService;

    @Autowired
    public GroupResourcesImpl(GroupService groupService, UserService userService) {
        this.groupService = Objects.requireNonNull(groupService, "Group service is a mandatory dependency.");
        this.userService = userService;
    }

    /**
     * Cria um grupo.
     *
     * @param groupCreationDTO
     **/
    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createGroup(@RequestBody GroupCreationDTO groupCreationDTO) throws UserNotFoundException {
        User creator = userService.findUserByUsername(groupCreationDTO.getCreatorUsername());

        groupService.createGroup(groupCreationDTO.getName(),creator);
    }

    /**
     * Busca um grupo pelo seu id.
     *
     * @param groupId
     * @return
     * @throws GroupNotExistsException
     */
    @RequestMapping(value = "/find/", method = RequestMethod.GET)
    public ResponseEntity<GroupDTO> findGroupById(@RequestParam String groupId) throws GroupNotExistsException {
        return new ResponseEntity<>(GroupDTOAssembler.fromGroup(groupService.findGroupById(groupId)), HttpStatus.OK);
    }

    /**
     * Associa um usuário ao grupo.
     *
     * @param joinGroupDTO
     * @throws GroupNotExistsException
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void joinGroup(@RequestBody JoinGroupDTO joinGroupDTO) throws GroupNotExistsException, UserNotFoundException {
        User userToJoin = userService.findUserByUsername(joinGroupDTO.getUserName());

        groupService.joinGroup(joinGroupDTO.getGroupId(),userToJoin);
    }

    /**
     * Retira um usuário de um grupo.
     *
     * @throws GroupNotExistsException
     */
    @RequestMapping(value = "/quit", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void quitGroup(@RequestBody QuitGroupDTO quitGroupDTO) throws GroupNotExistsException, UserNotFoundException {
        User userToQuit = userService.findUserByUsername(quitGroupDTO.getUserName());

        groupService.joinGroup(quitGroupDTO.getGroupId(),userToQuit);
    }

    /**
     * Lista os grupos que o usuário está associado.
     *
     * @param username
     * @return
     */
    @RequestMapping("/list")
    public List<GroupDTO> listGroups(@RequestParam String username) throws GroupNotExistsException, UserNotFoundException {
        List<Group> userGroups = groupService.listGroups(username);

        return GroupDTOAssembler.fromGroupList(userGroups);
    }

    @RequestMapping("/listall")
    public List<GroupDTO> listGroups() throws GroupNotExistsException {
        List<Group> userGroups = groupService.listGroups();

        return GroupDTOAssembler.fromGroupList(userGroups);
    }

    //EXCEPTION HANDLING
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({GroupNotExistsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForForbiddenException(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.FORBIDDEN.value(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ServerErrorDTO> exceptionHandlerForResourceNotFoundExceptions(HttpServletRequest req, Exception e) {
        return new ResponseEntity<ServerErrorDTO>(new ServerErrorDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
