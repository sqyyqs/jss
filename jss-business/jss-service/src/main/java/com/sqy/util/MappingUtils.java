package com.sqy.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;


@Log4j2
public final class MappingUtils {
    private MappingUtils() {
    }

    public static final String EMPTY_JSON = "{}";
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String convertObjectToJson(@Nullable Object object) {
        if (object != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(object);
            } catch (JsonProcessingException ex) {
                log.error("Can't create json for object: {}.", object);
            }
        }
        return EMPTY_JSON;
    }

    @Nullable
    public static <T> T parseJsonToInstance(@Nullable String json, Class<T> type) {
        if (StringUtils.hasText(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, type);
            } catch (JsonProcessingException ex) {
                log.error("Can't parse json: '{}' to instance.", json, ex);
            }
        }
        return null;
    }
}
