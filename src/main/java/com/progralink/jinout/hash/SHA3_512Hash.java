package com.progralink.jinout.hash;

import com.progralink.jinout.JavaVersion;

/**
 * Requires Java 9+
 */
public class SHA3_512Hash extends AbstractMessageDigestHash {

    public SHA3_512Hash() {
        super("SHA3-512");
    }

    @Override
    public boolean isSupported() {
        return JavaVersion.MAJOR >= 9;
    }

}
