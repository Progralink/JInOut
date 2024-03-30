package com.progralink.jinout;

import java.security.SecureRandom;
import java.util.Arrays;

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
