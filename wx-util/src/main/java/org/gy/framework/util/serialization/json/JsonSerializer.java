package org.gy.framework.util.serialization.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerConstant;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonSerializer implements Serializer {

    public static JsonSerializer INSTANCE = new JsonSerializer();

    private JSONWriter buildJSONWriter(OutputStream os) {
        JSONWriter writer = new JSONWriter(new OutputStreamWriter(os));
        writer.config(SerializerFeature.WriteClassName, true);
        writer.config(SerializerFeature.WriteMapNullValue, false);
        return writer;
    }

    private JSONReader builderJSONReader(InputStream is) {
        return new JSONReader(new InputStreamReader(is));
    }

    @Override
    public Serialization getSerialization() {
        return Serialization.JSON;
    }

    @Override
    public String getContentType() {
        return SerializerConstant.JSON_CONTENT_TYPE;
    }

    @Override
    public void serialize(OutputStream os,
                          Object object) throws IOException {
        JSONWriter writer = buildJSONWriter(os);
        writer.writeObject(object);
        writer.flush();
        os.flush();
    }

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializerConstant.BUFFER_SIZE);
        serialize(baos, object);
        return baos.toByteArray();
    }

    @Override
    public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
        JSONReader reader = builderJSONReader(is);
        return reader.readObject();
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
