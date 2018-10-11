package br.ufrn.chat.client;

import br.ufrn.chat.config.ServerConfig;
import br.ufrn.chat.exception.ChatApiExceptionHandler;
import br.ufrn.dto.GroupDTO;
import br.ufrn.requestbody.GroupCreationDTO;
import br.ufrn.requestbody.JoinGroupDTO;
import br.ufrn.requestbody.QuitGroupDTO;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Cliente restful para os serviços de grupo
 */
public class GroupResourceClient {

    public void createGroup(GroupCreationDTO groupCreationDTO) {

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(String.format(ServerConfig.CREATE_GROUP_URL, ServerConfig.SERVER_BASE_URL), groupCreationDTO, String.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }

    }

    public GroupDTO findGroupById(String groupId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(String.format(ServerConfig.FIND_GROUP_URL, ServerConfig.SERVER_BASE_URL, groupId), GroupDTO.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }

    public void joinGroup(JoinGroupDTO joinGroupDTO) {

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(String.format(ServerConfig.JOIN_GROUP_URL, ServerConfig.SERVER_BASE_URL), joinGroupDTO, String.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }

    public void quitGroup(QuitGroupDTO quitGroupDTO) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(String.format(ServerConfig.QUIT_GROUP_URL, ServerConfig.SERVER_BASE_URL), quitGroupDTO, String.class);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }

    public List<GroupDTO> listGroups(String username) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            GroupDTO[] forObject = restTemplate.getForObject(String.format(ServerConfig.FIND_USER_GROUPS_URL, ServerConfig.SERVER_BASE_URL, username), GroupDTO[].class);

            return Arrays.asList(forObject);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }

    public List<GroupDTO> listGroups() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            GroupDTO[] forObject = restTemplate.getForObject(String.format(ServerConfig.FIND_ALL_GROUPS_URL, ServerConfig.SERVER_BASE_URL), GroupDTO[].class);

            return Arrays.asList(forObject);
        } catch (HttpStatusCodeException ex) {
            throw new ClientException(ChatApiExceptionHandler.getExceptionHttpExceptionCause(ex));
        }
    }
}
