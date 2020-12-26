package com.example.retailer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtil {
    public static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            logger.info("序列化时出现错误，对象：" + obj.toString());
            return null;
        }
    }

    public static <T> T deserialize(String s, Class<T> clazz) {
        try {
            return mapper.readValue(s, clazz);
        } catch (JsonProcessingException exception) {
            logger.info("反序列化时出现错误，字符串：" + s);
            return null;
        }
    }
}
