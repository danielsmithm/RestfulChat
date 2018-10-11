package br.ufrn.chat.utils;

import br.ufrn.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DTOAssembler {

    public static <T> T assemble(String data, Class<T> tClass){

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(data,tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
