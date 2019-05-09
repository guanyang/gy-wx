package org.gy.framework.util.serialization.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerConstant;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

public class HessianSerializer implements Serializer {

    public static HessianSerializer        INSTANCE = new HessianSerializer();

    private static final SerializerFactory DEFAULT_SERIALIZER_FACTORY;

    static {
        DEFAULT_SERIALIZER_FACTORY = new SerializerFactory() {
            @Override
            public ClassLoader getClassLoader() {
                return Thread.currentThread().getContextClassLoader();
            }
        };
    }

    private Hessian2Output buildHessian2Output(OutputStream os) {
        Hessian2Output hessian2Output = new Hessian2Output(os);
        hessian2Output.setSerializerFactory(HessianSerializer.DEFAULT_SERIALIZER_FACTORY);
        return hessian2Output;
    }

    private Hessian2Input buildHessian2Input(InputStream is) {
        Hessian2Input hessian2Input = new Hessian2Input(is);
        hessian2Input.setSerializerFactory(HessianSerializer.DEFAULT_SERIALIZER_FACTORY);
        return hessian2Input;
    }

    @Override
    public String getContentType() {
        return SerializerConstant.HESSIAN_CONTENT_TYPE;
    }

    @Override
    public Serialization getSerialization() {
        return Serialization.HESSIAN;
    }

    @Override
    public void serialize(OutputStream os,
                          Object object) throws IOException {
        Hessian2Output output = buildHessian2Output(os);
        output.writeObject(object);
        output.flush();
    }

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializerConstant.BUFFER_SIZE);
        serialize(baos, object);
        return baos.toByteArray();
    }

    @Override
    public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
        Hessian2Input input = buildHessian2Input(is);
        return input.readObject();
    }

    @Override
    public Object deserialize(InputStream is,
                              Class<?> clz) throws IOException, ClassNotFoundException {
        Hessian2Input input = buildHessian2Input(is);
        return input.readObject(clz);
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        return deserialize(byteStream);
    }

}
