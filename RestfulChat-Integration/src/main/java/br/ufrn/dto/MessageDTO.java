package br.ufrn.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable {

    /**
     * Id do grupo.
     */
    private String groupId;

    /**
     * Nome do grupo para o qual a mensagem foi enviada.
     */
    private String groupName;

    /**
     * Username do autor da mensagem.
     */
    private String authorUserName;

    /**
     * Conteúdo da mensagem.
     */
    private String content;

    /**
     * Hora do instante que a mensagem foi enviada.
     */
    private Date sendTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
