package com.progralink.jinout.hash;

import com.progralink.jinout.JavaVersion;

/**
 * Requires Java 9+
 */
public class SHA3_384Hash extends AbstractMessageDigestHash {

    public SHA3_384Hash() {
        super("SHA3-384");
    }

    @Override
    public boolean isSupported() {
        return JavaVersion.MAJOR >= 9;
    }

}
