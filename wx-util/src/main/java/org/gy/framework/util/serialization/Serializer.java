package org.gy.framework.util.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    String getContentType();

    Serialization getSerialization();

    void serialize(OutputStream os,
                   Object object) throws IOException;

    byte[] serialize(Object object) throws IOException;

    Object deserialize(InputStream is) throws IOException, ClassNotFoundException;

    Object deserialize(InputStream is,
                       Class<?> clz) throws IOException, ClassNotFoundException;

    Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException;

}
