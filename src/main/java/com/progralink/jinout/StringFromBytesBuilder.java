package com.progralink.jinout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringFromBytesBuilder {
    private byte[] buffer;
    private int index = 0;


    public StringFromBytesBuilder() {
        this(3);
    }

    public StringFromBytesBuilder(int bufferLength) {
        this.buffer = new byte[bufferLength];
    }


    public StringFromBytesBuilder append(byte b) {
        if (index == buffer.length) {
            byte[] newBuffer = new byte[buffer.length * 2];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[index++] = b;
        return this;
    }

    public StringFromBytesBuilder append(byte[] bytes) {
        for (byte b : bytes) {
            append(b);
        }
        return this;
    }

    public StringFromBytesBuilder append(byte[] bytes, int offset, int length) {
        for (int i = offset; i < length; i++) {
            append(bytes[i]);
        }
        return this;
    }

    public int getLength() {
        return index;
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toStringUTF8() {
        return toString(StandardCharsets.UTF_8);
    }

    public String toString(Charset charset) {
        BOM bom;
        if (charset == null) {
            bom = BOM.findMatching(buffer);
            if (bom == null) {
                charset = StandardCharsets.UTF_8;
            }
        } else {
            bom = BOM.forCharset(charset);
            if (bom != null && !bom.matches(buffer)) {
                bom = null;
            }
        }

        if (bom != null) {
            return new String(buffer, bom.getByteLength(), index - bom.getByteLength(), bom.getCharset());
        }

        return new String(buffer, 0, index, charset);
    }


    public static String read(InputStream in) throws IOException {
        return read(in, null);
    }

    public static String read(InputStream in, Charset charset) throws IOException {
        StringFromBytesBuilder builder = new StringFromBytesBuilder();
        int r;
        while ((r = in.read()) > 0) {
            builder.append((byte) r);
        }
        return builder.toString(charset);
    }
}
