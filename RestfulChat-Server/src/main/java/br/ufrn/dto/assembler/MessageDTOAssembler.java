package br.ufrn.dto.assembler;

import br.ufrn.domain.Message;
import br.ufrn.dto.MessageDTO;

public class MessageDTOAssembler {

    public static MessageDTO fromMessage(Message message) {
        if (message == null) {
            return null;
        }

        MessageDTO dto = new MessageDTO();

        dto.setAuthorUserName(message.getAuthorUserName());
        dto.setContent(message.getContent());
        dto.setGroupId(message.getGroupId());
        dto.setSendTime(message.getSendTime());
        dto.setGroupName(message.getGroupName());

        return dto;
    }


}
