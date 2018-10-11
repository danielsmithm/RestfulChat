package br.ufrn.dto.assembler;

import br.ufrn.domain.Group;
import br.ufrn.dto.GroupDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupDTOAssembler {

    public static GroupDTO fromGroup(Group group){

        if(group == null){
            return null;
        }

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());

        return groupDTO;
    }

    public static List<GroupDTO> fromGroupList(List<Group> groups) {
        if(groups != null){
            return groups.stream().map(group -> fromGroup(group)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
