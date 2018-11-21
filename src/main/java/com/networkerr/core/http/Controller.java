package com.networkerr.core.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networkerr.core.dao.Model;

import java.io.IOException;

public class Controller {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Marshals json to model
     * @param requestJson Json request
     * @param model any Class extending Model
     * @return Object marshaled request
     * @throws IOException see Jackson
     */
    protected Object mapFromJson(String requestJson, Class<? extends Model> model) {
        try {
            return mapper.readValue(requestJson, model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String mapToJson(Object c) throws JsonProcessingException {
       return mapper.writeValueAsString(c);
    }
}
