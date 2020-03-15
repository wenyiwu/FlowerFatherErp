package com.flower.father.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtils {
    public static final Gson gson = new Gson();
    public static ObjectMapper objectMapper = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static JsonNode getJsonNode(String detailJson) throws IOException {
        return objectMapper.readTree(detailJson);
    }

    public static String writeValueAsString(JsonNode jsonNode) throws IOException {
        return objectMapper.writeValueAsString(jsonNode);
    }
}
