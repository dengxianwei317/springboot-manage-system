package com.demo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /*
     * 序列化
     * */
    public static String toJsonString(Object object) {
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /*
     * 反序列化
     * */
    public static <T> T toJavaBean(String json, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
