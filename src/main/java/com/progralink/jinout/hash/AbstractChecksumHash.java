package com.progralink.jinout.hash;

import com.progralink.jinout.BasicDataTypes;
import com.progralink.jinout.streams.IOStreams;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

public abstract class AbstractChecksumHash extends AbstractHash {
    private Supplier<Checksum> supplier;

    public AbstractChecksumHash(Supplier<Checksum> supplier) {
        this.supplier = supplier;
    }

    @Override
    public byte[] compute(InputStream in) throws IOException {
        Checksum checksum = supplier.get();
        try (CheckedInputStream checkedInputStream = new CheckedInputStream(in, checksum)) {
            byte[] buffer = new byte[IOStreams.DEFAULT_BYTE_BUFFER_SIZE];
            while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {
            }
            return BasicDataTypes.fromLong(checksum.getValue());
        }
    }

    @Override
    public byte[] compute(byte[] data, int offset, int length) {
        Checksum checksum = supplier.get();
        checksum.update(data, offset, length);
        return BasicDataTypes.fromLong(checksum.getValue());
    }
}
