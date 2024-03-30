package com.progralink.jinout.streams.input;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.progralink.jinout.streams.IOStreams.EOF;

public class EndHandlingInputStream extends FilterInputStream {
    private boolean reachedEOF = false;

    protected EndHandlingInputStream(InputStream in) {
        super(in);
    }

    public boolean isReachedEOF() {
        return reachedEOF;
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b == EOF) {
            onEndDetected();
        }
        return b;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = super.read(b, off, len);
        if (r == EOF) {
            onEndDetected();
        }
        return r;
    }

    protected void onEndDetected() {
        if (!reachedEOF) {
            handleEnd();
            reachedEOF = true;
        }
    }

    protected void handleEnd() {
    }
}
