package br.ufrn.repository;

import br.ufrn.domain.User;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BaseRepository<T> {
    /**
     * Entidades armazenadas
     */
    private Set<T> entitySet;

    public BaseRepository() {
        this.entitySet = new HashSet<>();
    }

    public Stream<T> findByPredicate(Predicate<T> predicate) {
        return entitySet.stream().filter(predicate);
    }

    public void add(T entity) {
        entitySet.add(entity);
    }

    public Set<T> findAll(){
        return new CopyOnWriteArraySet<>(entitySet);
    }

}
