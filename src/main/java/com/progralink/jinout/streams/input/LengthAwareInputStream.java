package com.progralink.jinout.streams.input;

import com.progralink.jinout.streams.IOStreams;

import java.io.IOException;
import java.io.InputStream;

public class LengthAwareInputStream extends PositionAwareInputStream {
    private final long initialByteLength;

    public LengthAwareInputStream(InputStream in, long length) {
        super(in);
        this.initialByteLength = length;
    }

    public long getRemainingByteLength() {
        return initialByteLength - getPosition();
    }

    @Override
    public int available() throws IOException {
        long remainingByteLength = getRemainingByteLength();
        if (remainingByteLength > 0) {
            int available = super.available();
            if (available > remainingByteLength) {
                return (int)remainingByteLength;
            }
            return available;
        }
        return 0;
    }

    @Override
    public int read() throws IOException {
        if (getRemainingByteLength() <= 0) {
            return IOStreams.EOF;
        }

        return super.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        long remainingByteLength = getRemainingByteLength();
        if (remainingByteLength <= 0) {
            return IOStreams.EOF;
        }

        if (len > remainingByteLength) {
            len = (int) remainingByteLength;
        }

        return super.read(b, off, len);
    }

    public byte[] readAll() throws IOException {
        return IOStreams.readFully(this, Math.toIntExact(getRemainingByteLength()));
    }

    @Override
    public long skip(long n) throws IOException {
        long remainingByteLength = getRemainingByteLength();
        if (n > remainingByteLength) {
            n = remainingByteLength;
        }

        return super.skip(n);
    }

    public long getInitialByteLength() {
        return initialByteLength;
    }
}
