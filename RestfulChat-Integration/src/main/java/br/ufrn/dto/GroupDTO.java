package br.ufrn.dto;

import java.io.Serializable;

public class GroupDTO implements Serializable {

    /**
     * Identificador do grupo.
     */
    private String id;

    /**
     * Nome do grupo.
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s - %s ",getId(),getName());
    }

}
