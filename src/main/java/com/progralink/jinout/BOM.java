package com.progralink.jinout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public enum BOM {
    UTF_8(StandardCharsets.UTF_8, new byte[] { (byte)239, (byte)187, (byte)191 }),
    UTF_32BE(Charset.forName("UTF-32"), new byte[] { (byte)0, (byte)0, (byte)254, (byte)255 }),
    UTF_32LE(Charset.forName("UTF-32LE"), new byte[] { (byte)255, (byte)254, (byte)0, (byte)0 }),
    UTF_16BE(StandardCharsets.UTF_16BE, new byte[] { (byte)254, (byte)255 }),
    UTF_16LE(StandardCharsets.UTF_16LE, new byte[] { (byte)255, (byte)254 });


    private final Charset charset;
    private final byte[] magicBytes;


    BOM(Charset charset, byte[] magicBytes) {
        this.charset = charset;
        this.magicBytes = magicBytes;
    }

    public int getByteLength() {
        return magicBytes.length;
    }

    public Charset getCharset() {
        return charset;
    }

    public byte[] getMagicBytes() {
        return Bytes.clone(magicBytes);
    }

    public boolean matches(byte[] data) {
        for (int i = 0; i < magicBytes.length; i++) {
            if (data[i] != magicBytes[i]) {
                return false;
            }
        }
        return true;
    }

    public byte[] toBytesWithBOM(String string) {
        return Bytes.concat(magicBytes, string.getBytes(charset));
    }


    public static BOM forCharset(Charset charset) {
        for (BOM bom : BOM.values()) {
            if (Objects.equals(bom.getCharset().name(), charset.name())) {
                return bom;
            }
        }
        return null;
    }

    public static BOM findMatching(byte[] data) {
        for (BOM bom : BOM.values()) {
            if (bom.matches(data)) {
                return bom;
            }
        }
        return null;
    }
}
