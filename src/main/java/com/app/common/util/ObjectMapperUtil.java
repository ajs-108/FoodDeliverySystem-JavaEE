package com.app.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> T toObject(BufferedReader reader, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(reader, targetClass);
    }

    public static <T> T toObject(BufferedReader reader, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(reader, valueTypeRef);
    }

    public static <T> T toObject(InputStream reader, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(reader, targetClass);
    }

    public static <T> T toObject(InputStream reader, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(reader, valueTypeRef);
    }

    public static <T> String toString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
