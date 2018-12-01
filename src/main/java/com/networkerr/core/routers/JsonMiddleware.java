package com.networkerr.core.routers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networkerr.core.dao.Model;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

import java.io.IOException;

public class JsonMiddleware {
    protected final ObjectMapper mapper = new ObjectMapper();
    protected String mapToJson(FullHttpRequest msg, Class<?> cls) {
        String json = msg.content().toString(CharsetUtil.UTF_8);
        Object responseObject = null;
        String response = null;
        try {
            responseObject = mapper.readValue(json, cls);
            response = mapper.writeValueAsString(responseObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        return response;
    }

    protected String writeModelAsJson(Object model) {
        String response = "";
        try {
            response = this.mapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
