package org.gy.framework.util.serialization.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerConstant;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;

public class KryoSerializer implements Serializer {

    public static KryoSerializer INSTANCE       = new KryoSerializer();

    private SimpleKryoPool       simpleKryoPool = new SimpleKryoPool(64);

    @Override
    public Serialization getSerialization() {
        return Serialization.KRYO;
    }

    @Override
    public String getContentType() {
        return SerializerConstant.STREAM_CONTENT_TYPE;
    }

    @Override
    public void serialize(final OutputStream os,
                          final Object object) throws IOException {
        simpleKryoPool.run(new KryoCallback<Object>() {
            @Override
            public Object execute(Kryo kryo) {
                Output output = new Output(os, SerializerConstant.BUFFER_SIZE);
                kryo.writeClassAndObject(output, object);
                output.flush();
                return null;
            }
        });
    }

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializerConstant.BUFFER_SIZE);
        serialize(baos, object);
        return baos.toByteArray();
    }

    @Override
    public Object deserialize(final InputStream is) throws IOException, ClassNotFoundException {

        return simpleKryoPool.run(new KryoCallback<Object>() {
            @Override
            public Object execute(Kryo kryo) {
                Input input = new Input(is, SerializerConstant.BUFFER_SIZE);
                return kryo.readClassAndObject(input);
            }
        });
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
