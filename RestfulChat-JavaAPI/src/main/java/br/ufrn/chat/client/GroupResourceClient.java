package br.ufrn.chat.client;

import br.ufrn.chat.config.ServerConfig;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.UserDTO;
import br.ufrn.requestbody.GroupCreationDTO;
import br.ufrn.requestbody.JoinGroupDTO;
import br.ufrn.requestbody.QuitGroupDTO;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class GroupResourceClient {
    public void createGroup(GroupCreationDTO groupCreationDTO) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(ServerConfig.SERVER_BASE_URL+"/groups/create",groupCreationDTO,String.class);

    }

    public GroupDTO findGroupById(String groupId) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(ServerConfig.SERVER_BASE_URL+"/groups/find/?groupId="+groupId,GroupDTO.class);
    }

    public void joinGroup(JoinGroupDTO joinGroupDTO) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(ServerConfig.SERVER_BASE_URL+"/groups/join",joinGroupDTO,String.class);
    }

    public void quitGroup(QuitGroupDTO quitGroupDTO) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(ServerConfig.SERVER_BASE_URL+"/groups/quit",quitGroupDTO,String.class);
    }

    public List<GroupDTO> listGroups(String username) {
        RestTemplate restTemplate = new RestTemplate();

        GroupDTO[] forObject = restTemplate.getForObject(ServerConfig.SERVER_BASE_URL + "/groups/list/?username=" + username, GroupDTO[].class);

        return Arrays.asList(forObject);
    }

    public List<GroupDTO> listGroups() {
        RestTemplate restTemplate = new RestTemplate();

        GroupDTO[] forObject = restTemplate.getForObject(ServerConfig.SERVER_BASE_URL + "/groups/listall", GroupDTO[].class);

        return Arrays.asList(forObject);
    }
}
