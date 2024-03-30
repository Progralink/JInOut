package com.progralink.jinout;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicDataTypesTest {
    @Test
    void testShort() throws IOException {
        short testVal = 25012;
        byte[] serialized = BasicDataTypes.fromShort(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
        byteBuffer.putShort(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        int deserialized = BasicDataTypes.toShort(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeShort(out, testVal);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readShort(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testInteger() throws IOException {
        int testVal = 385030221;
        byte[] serialized = BasicDataTypes.fromInt(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        int deserialized = BasicDataTypes.toInt(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeInt(out, testVal);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readInt(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testLong() throws IOException {
        long testVal = 38503022153243L;
        byte[] serialized = BasicDataTypes.fromLong(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        long deserialized = BasicDataTypes.toLong(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeLong(out, testVal);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readLong(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testFloat() throws IOException {
        float testVal = 0.123f;
        byte[] serialized = BasicDataTypes.fromFloat(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        float deserialized = BasicDataTypes.toFloat(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeFloat(out, testVal);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readFloat(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testDouble() throws IOException {
        double testVal = 0.123;
        byte[] serialized = BasicDataTypes.fromDouble(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        double deserialized = BasicDataTypes.toDouble(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeDouble(out, testVal);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readDouble(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testString() throws IOException {
        String testVal = "Hello World!";
        byte[] serialized = BasicDataTypes.fromString(testVal, false);
        byte[] comparative = testVal.getBytes(StandardCharsets.UTF_8);
        assertArrayEquals(comparative, serialized);
        String deserialized = BasicDataTypes.toStringUTF8(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeString(out, testVal, false);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readString(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testStringWithEndingMark() throws IOException {
        String testVal = "Hello World!";
        byte[] serialized = BasicDataTypes.fromString(testVal, true);
        byte[] comparative = Bytes.concat(testVal.getBytes(StandardCharsets.UTF_8), new byte[] { 0 });
        assertArrayEquals(comparative, serialized);
        String deserialized = BasicDataTypes.toStringUTF8(serialized);
        assertEquals(testVal, deserialized);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BasicDataTypes.writeString(out, testVal, true);
            serialized = out.toByteArray();
        }
        assertArrayEquals(comparative, serialized);

        try (InputStream in = new ByteArrayInputStream(serialized)) {
            deserialized = BasicDataTypes.readString(in);
        }
        assertEquals(testVal, deserialized);
    }

    @Test
    void testStringsStream() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BasicDataTypes.writeString(out, "Hello", true);
        BasicDataTypes.writeString(out, "World", true);
        BasicDataTypes.writeString(out, "łąka!", true);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        assertEquals("Hello", BasicDataTypes.readString(in));
        assertEquals("World", BasicDataTypes.readString(in));
        assertEquals("łąka!", BasicDataTypes.readString(in));
    }

    @Test
    void testHex() {
        assertEquals("4F4B", BasicDataTypes.toStringHex(new byte[] { 'O', 'K' }));
    }
}
