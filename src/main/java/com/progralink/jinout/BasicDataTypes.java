package com.progralink.jinout;

import com.progralink.jinout.streams.IOStreams;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class BasicDataTypes {
    private static byte[] SINGLE_BYTE_ZERO = new byte[] { 0 };

    private BasicDataTypes() { }

    public static byte readByte(InputStream in) throws IOException {
        return (byte) readByteAsInt(in);
    }

    public static int readByteAsInt(InputStream in) throws IOException {
        int r = in.read();
        if (r < 0) {
            throw new EOFException();
        }
        return r;
    }

    public static byte[] fromByte(byte value) {
        return new byte[] { value };
    }

    public static void fromByte(byte value, byte[] target, int offset) {
        target[offset] = value;
    }

    public static byte[] fromDouble(double value) {
        return fromLong(Double.doubleToLongBits(value));
    }

    public static void fromDouble(double value, byte[] target, int offset) {
        byte[] bytes = fromDouble(value);
        System.arraycopy(bytes, 0, target, offset, bytes.length);
    }

    public static void writeDouble(OutputStream out, double value) throws IOException {
        writeLong(out, Double.doubleToLongBits(value));
    }

    public static byte[] fromFloat(float value) {
        return fromInt(Float.floatToIntBits(value));
    }

    public static void fromFloat(float value, byte[] target, int offset) {
        byte[] bytes = fromFloat(value);
        System.arraycopy(bytes, 0, target, offset, bytes.length);
    }

    public static void writeFloat(OutputStream out, float value) throws IOException {
        writeInt(out, Float.floatToIntBits(value));
    }

    public static byte[] fromShort(short value) {
        byte[] result = new byte[2];
        fromShort(value, result, 0);
        return result;
    }

    public static void fromShort(short value, byte[] target, int offset) {
        target[offset] = (byte)(value >> 8);
        target[offset+1] = (byte)value;
    }

    public static void writeShort(OutputStream out, short value) throws IOException {
        out.write((byte)(value >> 8));
        out.write((byte)(value));
    }

    public static byte[] fromInt(int value) {
        byte[] result = new byte[4];
        fromInt(value, result, 0);
        return result;
    }

    public static void fromInt(int value, byte[] target, int offset) {
        target[offset] = (byte)(value >> 24);
        target[offset+1] = (byte)(value >> 16);
        target[offset+2] = (byte)(value >> 8);
        target[offset+3] = (byte)value;
    }

    public static void writeInt(OutputStream out, int value) throws IOException {
        out.write(value >> 24);
        out.write(value >> 16);
        out.write(value >> 8);
        out.write(value);
    }

    public static byte[] fromLong(long value) {
        byte[] result = new byte[8];
        fromLong(value, result, 0);
        return result;
    }

    public static void fromLong(long value, byte[] target, int offset) {
        target[offset] = (byte)(value >> 56);
        target[offset+1] = (byte)(value >> 48);
        target[offset+2] = (byte)(value >> 40);
        target[offset+3] = (byte)(value >> 32);
        target[offset+4] = (byte)(value >> 24);
        target[offset+5] = (byte)(value >> 16);
        target[offset+6] = (byte)(value >> 8);
        target[offset+7] = (byte)value;
    }

    public static void writeLong(OutputStream out, long value) throws IOException {
        out.write(fromLong(value));
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
                    SINGLE_BYTE_ZERO
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

    public static void writeString(OutputStream out, String string, boolean withEndOfStringMark) throws IOException {
        writeStringUTF8(out, string, withEndOfStringMark);
    }

    public static void writeStringUTF8(OutputStream out, String string, boolean withEndOfStringMark) throws IOException {
        writeString(out, string, StandardCharsets.UTF_8, withEndOfStringMark);
    }

    public static void writeString(OutputStream out, String string, Charset charset, boolean withEndOfStringMark) throws IOException {
        byte[] bytes = string.getBytes(charset);
        for (byte b : bytes) {
            if (b == 0) {
                throw new IllegalStateException("Encoded string should not contain NUL-byte");
            }
        }

        out.write(bytes);
        if (withEndOfStringMark) {
            out.write(0);
        }
    }

    public static double toDouble(byte[] data) {
        return toDouble(data, 0);
    }

    public static double toDouble(byte[] data, int offset) {
        return Double.longBitsToDouble(toLong(data, offset));
    }

    public static double readDouble(InputStream in) throws IOException {
        return Double.longBitsToDouble(readLong(in));
    }

    public static float toFloat(byte[] data) {
        return toFloat(data, 0);
    }

    public static float toFloat(byte[] data, int offset) {
        return Float.intBitsToFloat(toInt(data, offset));
    }

    public static float readFloat(InputStream in) throws IOException {
        return Float.intBitsToFloat(readInt(in));
    }

    public static int toInt(byte[] data) {
        return toInt(data, 0);
    }

    public static int toInt(byte[] data, int offset) {
        return
                (data[offset] << 24) & 0xff000000 |
                        (data[offset + 1] <<16) & 0x00ff0000 |
                        (data[offset + 2] << 8) & 0x0000ff00 |
                        (data[offset + 3]) & 0x000000ff;
    }

    public static int readInt(InputStream in) throws IOException {
        int b1 = in.read();
        int b2 = in.read();
        int b3 = in.read();
        int b4 = in.read();
        if (b1 < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
            throw new EOFException();
        }
        return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
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

    public static long readLong(InputStream in) throws IOException {
        return toLong(IOStreams.readExactly(in, Long.BYTES));
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

    public static short readShort(InputStream in) throws IOException {
        int b1 = in.read();
        if (b1 < 0) {
            throw new EOFException();
        }
        int b2 = in.read();
        if (b2 < 0) {
            throw new EOFException();
        }
        return (short)((b1 << 8) + b2);
    }


    public static String toStringUTF8(byte[] bytes) {
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

    public static String toStringBytes(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(Byte.toUnsignedInt(b));
        }
        return builder.toString();
    }


    public static String readString(InputStream in) throws IOException {
        return readString(in, null);
    }

    public static String readStringUTF8(InputStream in) throws IOException {
        return readString(in, StandardCharsets.UTF_8);
    }

    public static String readString(InputStream in, Charset charset) throws IOException {
        return StringFromBytesBuilder.read(in, charset);
    }
}
