package org.gy.framework.util.json;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonMapper {
    private static final Logger       logger = LoggerFactory.getLogger(JacksonMapper.class);
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * JSON对象转换为JavaBean
     * 
     * @param json JSON对象
     * @param valueType Bean类
     * @return 泛型对象
     */
    public static <T> T jsonToBean(String json,
                                   Class<T> valueType) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JavaBean转换为JSON字符串
     * 
     * @param bean JavaBean对象
     * @return json字符串
     */
    public static String beanToJson(Object bean) {
        try {
            return mapper.writeValueAsString(bean);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * json转成List
     * 
     * @param json json字符串
     * @param clazz List元素类
     * @return List集合
     */
    public static <T> List<T> jsonToList(String json,
                                         Class<T> clazz) {
        return jsonToBean(json, List.class, clazz);
    }

    /**
     * json转成集合
     * 
     * @param json json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return 泛型对象
     */
    public static <T> T jsonToBean(String json,
                                   Class<?> collectionClass,
                                   Class<?>... elementClasses) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            JavaType javaType = getCollectionType(collectionClass, elementClasses);
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 功能描述: JSON对象转换为JavaBean
     * 
     * @param json
     * @param valueTypeRef
     * @return
     */
    public static <T> T jsonToBean(String json,
                                   TypeReference<T> valueTypeRef) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     * 
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass,
                                              Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
