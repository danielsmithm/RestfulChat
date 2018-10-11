package br.ufrn.repository;

import br.ufrn.domain.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/***
 * Repositório para a entidade usuário.
 */
@Repository
public class UserRepository extends BaseRepository implements Serializable {

    public UserRepository() {
        super();
    }

    /**
     * Retorna se existe um usuário cadastrado com este username.
     * @param userName
     * @return
     */
    public boolean existsUserName(String userName){
        return findByPredicate(hasUsername(userName)).count() != 0;
    }

    public User findByUsername(String username) {
        Optional<User> userOption = findByPredicate(hasUsername(username)).findFirst();

        if(userOption.isPresent()){
            return userOption.get();
        }

        return null;
    }

    public Predicate<User> hasUsername(String username) {
        return user -> user.hasUserName(username);
    }

}
