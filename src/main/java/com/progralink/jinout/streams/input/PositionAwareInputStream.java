package com.progralink.jinout.streams.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.progralink.jinout.streams.IOStreams.EOF;

public class PositionAwareInputStream extends FilterInputStream {
    private long position;

    public PositionAwareInputStream(InputStream in) {
        super(in);
    }

    public long getPosition() {
        return position;
    }

    @Override
    public int read() throws IOException {
        int r = super.read();
        if (r != EOF) {
            position++;
        }
        return r;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = super.read(b, off, len);
        if (r > 0) {
            position += r;
        }
        return r;
    }

    @Override
    public long skip(long n) throws IOException {
        long skipped = super.skip(n);
        position += skipped;
        return skipped;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException();
    }
}
