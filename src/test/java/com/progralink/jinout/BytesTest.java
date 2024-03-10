package com.progralink.jinout;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BytesTest {
    @Test
    void testShort() {
        short testVal = 25012;
        byte[] serialized = Bytes.fromShort(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
        byteBuffer.putShort(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        int deserialized = Bytes.toShort(serialized);
        assertEquals(testVal, deserialized);
    }

    @Test
    void testInteger() {
        int testVal = 385030221;
        byte[] serialized = Bytes.fromInt(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.putInt(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        int deserialized = Bytes.toInt(serialized);
        assertEquals(testVal, deserialized);

    }

    @Test
    void testLong() {
        long testVal = 38503022153243L;
        byte[] serialized = Bytes.fromLong(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.putLong(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        long deserialized = Bytes.toLong(serialized);
        assertEquals(testVal, deserialized);
    }

    @Test
    void testFloat() {
        float testVal = 0.123f;
        byte[] serialized = Bytes.fromFloat(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        float deserialized = Bytes.toFloat(serialized);
        assertEquals(testVal, deserialized);
    }

    @Test
    void testDouble() {
        double testVal = 0.123;
        byte[] serialized = Bytes.fromDouble(testVal);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(testVal);
        byte[] comparative = byteBuffer.array();
        assertArrayEquals(comparative, serialized);

        double deserialized = Bytes.toDouble(serialized);
        assertEquals(testVal, deserialized);
    }

    @Test
    void testString() {
        byte[] serialized = Bytes.fromString("Hello World!", false);
        assertArrayEquals("Hello World!".getBytes(StandardCharsets.UTF_8), serialized);
        String deserialized = Bytes.toString(serialized);
        assertEquals("Hello World!", deserialized);
    }

    @Test
    void testStringWithEndingMark() {
        byte[] serialized = Bytes.fromString("Hello World!", true);
        assertArrayEquals(Bytes.concat("Hello World!".getBytes(StandardCharsets.UTF_8), new byte[] { 0 }), serialized);
        String deserialized = Bytes.toString(serialized);
        assertEquals("Hello World!", deserialized);
    }

    @Test
    void testHex() {
        assertEquals("4F4B", Bytes.toStringHex(new byte[] { 'O', 'K' }));
    }
}
