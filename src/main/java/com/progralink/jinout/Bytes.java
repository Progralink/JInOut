package com.progralink.jinout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public final class Bytes {
    private Bytes() { }

    public static byte[] clean(byte[] data) {
        if (data != null) {
            Arrays.fill(data, (byte) 0);
        }
        return null;
    }

    public static byte[] clone(byte[] data) {
        return clone(data, 0, data.length);
    }

    public static byte[] clone(byte[] data, int offset) {
        return clone(data, offset, data.length - offset);
    }

    public static byte[] clone(byte[] data, int offset, int length) {
        byte[] duplicated = new byte[length];
        System.arraycopy(data, offset, duplicated, 0, length);
        return duplicated;
    }

    public static int compare(byte[] a, byte[] b) {
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Byte.toUnsignedInt(a[i]) - Byte.toUnsignedInt(b[i]);
            }
        }
        return a.length - b.length;
    }

    public static byte[] concat(byte[]... args) {
        int length = 0;
        for (byte[] arg : args) {
            length += arg.length;
        }
        int offset = 0;
        byte[] target = new byte[length];
        for (byte[] arg : args) {
            if (arg != null) {
                System.arraycopy(arg, 0, target, offset, arg.length);
                offset += arg.length;
            }
        }
        return target;
    }

    public static boolean equals(byte[] array1, byte[] array2) {
        return Arrays.equals(array1, array2);
    }

    public static byte[] generateIncremental(int byteLength) {
        byte[] targetBytes = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            targetBytes[i] = (byte)(i % 255);
        }
        return targetBytes;
    }

    public static byte[] generateRandom(int byteLength) {
        byte[] bytes = new byte[byteLength];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    public static byte[] sub(byte[] source, int offset) {
        int length = source.length - offset;
        return sub(source, offset, length);
    }

    public static byte[] sub(byte[] source, int offset, int length) {
        if (offset == 0 && source.length == length) {
            return source;
        }
        byte[] target = new byte[length];
        System.arraycopy(source, offset, target, 0, length);
        return target;
    }

    public static String print(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append( Byte.toUnsignedInt(data[i]) );
        }
        return "[" + builder + "]";
    }

    public static byte[] fromByte(byte value) {
        return new byte[] { value };
    }

    public static byte[] fromDouble(double value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.putDouble(value);
        return byteBuffer.array();
    }

    public static byte[] fromFloat(float value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.putFloat(value);
        return byteBuffer.array();
    }

    public static byte[] fromShort(short value) {
        byte[] result = new byte[2];
        result[0] = (byte)(value >> 8);
        result[1] = (byte)value;
        return result;
    }

    public static byte[] fromInt(int value) {
        byte[] result = new byte[4];
        result[0] = (byte)(value >> 24);
        result[1] = (byte)(value >> 16);
        result[2] = (byte)(value >> 8);
        result[3] = (byte)value;
        return result;
    }

    public static byte[] fromLong(long value) {
        byte[] result = new byte[8];
        result[0] = (byte)(value >> 56);
        result[1] = (byte)(value >> 48);
        result[2] = (byte)(value >> 40);
        result[3] = (byte)(value >> 32);
        result[4] = (byte)(value >> 24);
        result[5] = (byte)(value >> 16);
        result[6] = (byte)(value >> 8);
        result[7] = (byte)value;
        return result;
    }

    public static byte[] fromString(String string) {
        return fromString(string, false);
    }

    public static byte[] fromString(String string, boolean withEndOfStringMark) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        if (withEndOfStringMark) {
            for (byte b : bytes) {
                if (b == 0) {
                    throw new IllegalStateException("Encoded UTF-8 string may never contain NUL-byte");
                }
            }

            return Bytes.concat(
                    bytes,
                    new byte[] {0}
            );
        }
        return bytes;
    }

    public static byte[] fromStringBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static byte[] fromStringBase64URL(String base64) {
        return Base64.getUrlDecoder().decode(base64);
    }

    public static double toDouble(byte[] data) {
        return toDouble(data, 0);
    }

    public static double toDouble(byte[] data, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
        byteBuffer.put(data, offset, Double.BYTES);
        byteBuffer.rewind();
        return byteBuffer.getDouble();
    }

    public static float toFloat(byte[] data) {
        return toFloat(data, 0);
    }

    public static float toFloat(byte[] data, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.put(data, offset, Float.BYTES);
        byteBuffer.rewind();
        return byteBuffer.getFloat();
    }

    public static int toInt(byte[] data) {
        return toInt(data, 0);
    }

    public static int toInt(byte[] data, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.put(sub(data, offset, Integer.BYTES));
        byteBuffer.rewind();
        return byteBuffer.getInt();
    }

    public static long toLong(byte[] data) {
        return toLong(data, 0);
    }

    public static long toLong(byte[] data, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.put(data, offset, Long.BYTES);
        byteBuffer.rewind();
        return byteBuffer.getLong();
    }

    public static short toShort(byte[] data) {
        return toShort(data, 0);
    }

    public static short toShort(byte[] data, int offset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES);
        byteBuffer.put(data, offset, Short.BYTES);
        byteBuffer.rewind();
        return byteBuffer.getShort();
    }

    public static String toString(byte[] bytes) {
        return toString(bytes, StandardCharsets.UTF_8);
    }

    public static String toString(byte[] bytes, Charset charset) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (int b : bytes) {
                if (b == 0) {
                    break;
                }
                baos.write(b);
            }
            return new String(baos.toByteArray(), charset);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String toStringHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            if (b == 0) {
                break;
            }
            builder.append(Integer.toHexString(b).toUpperCase());
        }
        return builder.toString();
    }

    public static String toStringBase64(byte[] bytes) {
        return toStringBase64(bytes, true);
    }

    public static String toStringBase64(byte[] bytes, boolean withPadding) {
        String s = Base64.getEncoder().encodeToString(bytes);
        if (!withPadding) {
            return s.replace("=", "");
        }
        return s;
    }

    public static String toStringBase64URL(byte[] bytes) {
        return toStringBase64URL(bytes, true);
    }

    public static String toStringBase64URL(byte[] bytes, boolean withPadding) {
        String s = Base64.getUrlEncoder().encodeToString(bytes);
        if (!withPadding) {
            return s.replace("=", "");
        }
        return s;
    }

    public static byte[] xor(byte[] left, byte[] right) {
        if (left.length != right.length) {
            throw new IllegalArgumentException("Same size byte arrays expected");
        }
        byte[] result = new byte[left.length];
        for (int i = 0; i < left.length; i++) {
            result[i] = (byte)(left[i] ^ right[i]);
        }
        return result;
    }

    public static byte[] zeroes(int length) {
        return new byte[length];
    }

}
