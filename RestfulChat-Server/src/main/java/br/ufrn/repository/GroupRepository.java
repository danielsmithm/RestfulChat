package br.ufrn.repository;

import br.ufrn.domain.Group;
import br.ufrn.domain.Message;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GroupRepository extends BaseRepository<Group> implements Serializable {

    private GroupRepository() {
        super();
    }

    /**
     * Metodo fábrica estático para o repositório de grupo.
     *
     * @return
     */
    public static GroupRepository createGroupRepository() {
        return new GroupRepository();
    }

    /**
     * Retorna um grupo pelo seu id.
     * @param groupId
     * @return
     */
    public Group findGroupById(String groupId){
        Optional<Group> first = findAll().stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst();

        if(first.isPresent()){
            return first.get();
        }

        //If the element don't exists
        return null;
    }

    /**
     * Adiciona um grupo ao repositório.
     *
     * @param group
     */
    public void add(Group group){
        super.add(group);
    }

    /**
     * Retorna todas as mensagens que o usuário recebeu.
     *
     * @param userName
     * @return
     */
    public List<Message> findMessagesByUser(String userName){

        return findAll().stream()
                .map(group -> group.getMessages())
                .flatMap(messages -> messages.stream())
                .filter(message -> message.fromUser(userName))
                .collect(Collectors.toList());

    }

    /**
     * Retorna todos os grupos que o usuário está associado.
     *
     * @param userName
     * @return
     */
    public List<Group> findGroupsByUser(String userName){

        return findAll().stream()
                .filter(group -> group.containsUser(userName))
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos os grupos existentes.
     *
     * @return
     */
    public List<Group> findAllGroups() {
        return super.findAll().stream().collect(Collectors.toList());
    }
}
