package org.gy.framework.util.serialization.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerConstant;

public class JavaSerializer implements Serializer {

    public static JavaSerializer INSTANCE = new JavaSerializer();

    @Override
    public Serialization getSerialization() {
        return Serialization.OBJ_STREAM;
    }

    @Override
    public String getContentType() {
        return SerializerConstant.JAVA_CONTENT_TYPE;
    }

    @Override
    public void serialize(OutputStream os,
                          Object object) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializerConstant.BUFFER_SIZE);
        serialize(baos, object);
        return baos.toByteArray();
    }

    @Override
    public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(is);
        return objectInputStream.readObject();
    }

    @Override
    public Object deserialize(InputStream is,
                              Class<?> clz) throws IOException, ClassNotFoundException {
        return deserialize(is);
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        return deserialize(byteStream);
    }

}
