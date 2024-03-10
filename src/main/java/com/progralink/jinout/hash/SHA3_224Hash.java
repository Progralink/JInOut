package com.progralink.jinout.hash;

import com.progralink.jinout.JavaVersion;

/**
 * Requires Java 9+
 */
public class SHA3_224Hash extends AbstractMessageDigestHash {

    public SHA3_224Hash() {
        super("SHA3-224");
    }

    @Override
    public boolean isSupported() {
        return JavaVersion.MAJOR >= 9;
    }

}
