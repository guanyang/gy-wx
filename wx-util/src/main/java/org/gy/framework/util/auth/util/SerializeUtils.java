package org.gy.framework.util.auth.util;

import java.io.IOException;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    private SerializeUtils() {

    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        Object result = null;
        if (bytes == null || bytes.length == 0) {
            return result;
        }
        Serializer serializer = SerializerFactory.getSerializer(Serialization.OBJ_STREAM);
        try {
            result = serializer.deserialize(bytes);
        } catch (ClassNotFoundException | IOException e) {
            logger.error("Failed to deserialize:" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        byte[] result = null;
        if (object == null) {
            return result;
        }
        Serializer serializer = SerializerFactory.getSerializer(Serialization.OBJ_STREAM);
        try {
            result = serializer.serialize(object);
        } catch (Exception e) {
            logger.error("Failed to serialize:" + e.getMessage(), e);
        }
        return result;
    }
}