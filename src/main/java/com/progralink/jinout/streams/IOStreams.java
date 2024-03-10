package com.progralink.jinout.streams;

import com.progralink.jinout.streams.input.LengthAwareInputStream;

import java.io.*;

public final class IOStreams {
    public static final int EOF = -1;
    public static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;

    private IOStreams() { }

    public static int compare(InputStream in1, InputStream in2) throws IOException {
        in1 = new BufferedInputStream(in1);
        in2 = new BufferedInputStream(in2);

        while (true) {
            int b1 = in1.read();
            int b2 = in2.read();
            if (b1 == EOF && b2 == EOF) {
                return 0;
            }
            if (b1 == EOF) {
                return -1;
            }
            if (b2 == EOF) {
                return 1;
            }
            if (b1 != b2) {
                return Math.toIntExact(b1) - Math.toIntExact(b2);
            }
        }
    }

    public static long consume(InputStream in) throws IOException {
        long length = 0;
        byte[] buffer = createByteArrayBuffer();
        int read;
        while ((read = in.read(buffer, 0, buffer.length)) >= 0) {
            length += read;
        }
        return length;
    }

    public static long consume(InputStream in, long length) throws IOException {
        long remaining = length;
        byte[] buffer = createByteArrayBuffer();
        int read;
        while ((read = in.read(buffer, 0, (int)Math.min(remaining, buffer.length))) >= 0) {
            length += read;
            remaining -= read;
        }
        return length;
    }

    public static byte[] createByteArrayBuffer() {
        return new byte[DEFAULT_BYTE_BUFFER_SIZE];
    }

    public static boolean equals(InputStream in1, InputStream in2) throws IOException {
        return compare(in1, in2) == 0;
    }

    public static Long getRemainingByteLength(InputStream in) {
        if (in instanceof LengthAwareInputStream) {
            return ((LengthAwareInputStream) in).getRemainingByteLength();
        } else if (in instanceof ByteArrayInputStream) {
            return (long)((ByteArrayInputStream)in).available();
        }
        return null;
    }

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

    public static byte[] readExactly(InputStream in, int length) throws IOException {
        if (length == 0) {
            return new byte[0];
        }

        int offset = 0;
        byte[] target = new byte[length];
        int r;
        while ((r = in.read(target, offset, length - offset)) > 0) {
            offset += r;
        }
        if (offset != length) {
            throw new EOFException();
        }
        return target;
    }

    public static byte[] readFully(InputStream in, int expectedLength) throws IOException {
        byte[] data = readExactly(in, expectedLength);
        if (in.available() > 0 || in.read() != EOF) {
            throw new IOException("Stream is longer than expected");
        }
        return data;
    }

    public static byte[] readFully(InputStream in) throws IOException {
        if (in instanceof LengthAwareInputStream) {
            return readFully(in, (int)((LengthAwareInputStream) in).getRemainingByteLength());
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            transfer(in, baos);
            return baos.toByteArray();
        }
    }

    public static void skipExactly(InputStream in, long n) throws IOException {
        long left = n;
        while (left > 0) {
            long skipped = in.skip(n);
            if (skipped < 0) {
                throw new IllegalStateException("Unable to properly skip");
            }
            else if (skipped == 0) {
                if (in.read() == EOF) {
                    throw new EOFException();
                }
                left--;
            } else {
                left -= skipped;
            }
        }

        if (left < 0) {
            throw new IllegalStateException("Unable to properly skip");
        }
    }


    public static long transfer(InputStream in, OutputStream out) throws IOException {
        long length = 0;
        byte[] buffer = createByteArrayBuffer();
        int read;
        while ((read = in.read(buffer, 0, buffer.length)) >= 0) {
            out.write(buffer, 0, read);
            length += read;
        }
        return length;
    }

}
