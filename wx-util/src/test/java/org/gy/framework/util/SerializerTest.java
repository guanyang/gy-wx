package org.gy.framework.util;

import java.io.IOException;
import java.io.Serializable;

import org.gy.framework.util.serialization.Serialization;
import org.gy.framework.util.serialization.Serializer;
import org.gy.framework.util.serialization.SerializerFactory;

public class SerializerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        testSerializer(500000);

    }

    private static void testSerializer(int times) throws ClassNotFoundException, IOException {
        testSerializer(Serialization.HESSIAN, times);
        testSerializer(Serialization.OBJ_STREAM, times);
        testSerializer(Serialization.JSON, times);
        testSerializer(Serialization.KRYO, times);
    }

    private static void testSerializer(Serialization serialization,
                                       int times) throws IOException, ClassNotFoundException {
        TestData testData = new TestData("test", 24);
        byte[] bytes = null;
        Serializer serializer = SerializerFactory.getSerializer(serialization);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            bytes = serializer.serialize(testData);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(serialization.toString() + "序列化耗时：" + (endTime - startTime) + "ms" + "\t次数：" + times);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            serializer.deserialize(bytes);
        }
        endTime = System.currentTimeMillis();
        System.out.println(serialization.toString() + "反序列化耗时：" + (endTime - startTime) + "ms" + "\t次数：" + times);
    }

    public static class TestData implements Serializable {
        private static final long serialVersionUID = 4560033931942805871L;
        private String            name;
        private int               age;

        public TestData() {
        }

        public TestData(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

}
