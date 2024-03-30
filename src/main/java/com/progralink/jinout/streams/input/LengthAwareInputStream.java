package com.progralink.jinout.streams.input;

import com.progralink.jinout.streams.IOStreams;

import java.io.IOException;
import java.io.InputStream;

import static com.progralink.jinout.streams.IOStreams.EOF;

public class LengthAwareInputStream extends PositionAwareInputStream {
    private final long initialByteLength;

    public LengthAwareInputStream(InputStream in, long length) {
        super(in);
        this.initialByteLength = length;
    }

    public long getInitialByteLength() {
        return initialByteLength;
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
            onEndDetected();
            return EOF;
        }

        int b = super.read();
        if (getRemainingByteLength() <= 0) {
            onEndDetected();
        }
        return b;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        long remainingByteLength = getRemainingByteLength();
        if (remainingByteLength <= 0) {
            onEndDetected();
            return EOF;
        }

        if (len > remainingByteLength) {
            len = (int) remainingByteLength;
        }

        int r = super.read(b, off, len);
        if (getRemainingByteLength() <= 0) {
            onEndDetected();
        }
        return r;
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

        long skipped = super.skip(n);
        if (getRemainingByteLength() <= 0) {
            onEndDetected();
        }
        return skipped;
    }
}
