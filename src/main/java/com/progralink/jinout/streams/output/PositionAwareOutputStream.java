package com.progralink.jinout.streams.output;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PositionAwareOutputStream extends FilterOutputStream {
    private long position = 0;

    public PositionAwareOutputStream(OutputStream out) {
        super(out);
    }

    public long getPosition() {
        return position;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
        if (b > -1) {
            position++;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        position += len;
    }
}
