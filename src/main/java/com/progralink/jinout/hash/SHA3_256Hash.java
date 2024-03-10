package com.progralink.jinout.hash;

import com.progralink.jinout.JavaVersion;

/**
 * Requires Java 9+
 */
public class SHA3_256Hash extends AbstractMessageDigestHash {

    public SHA3_256Hash() {
        super("SHA3-256");
    }

    @Override
    public boolean isSupported() {
        return JavaVersion.MAJOR >= 9;
    }

}
