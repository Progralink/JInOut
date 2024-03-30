package com.progralink.jinout;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class StringFromBytesBuilderTest {

    @Test
    void testUTF8withBOM() {
        test(StandardCharsets.UTF_8);
    }

    @Test
    void testUTF8withoutBOM() {
        StringFromBytesBuilder sb = new StringFromBytesBuilder();
        sb.append("Helloł Łorld".getBytes(StandardCharsets.UTF_8));
        assertEquals("Helloł Łorld", sb.toStringUTF8());
    }

    @Test
    void testUTF16LE() {
        test(StandardCharsets.UTF_16LE);
    }

    @Test
    void testUTF16BE() {
        test(StandardCharsets.UTF_16BE);
    }

    @Test
    void testUTF32BE() {
        test(Charset.forName("UTF-32"));
    }

    @Test
    void testUTF32LE() {
        test(Charset.forName("UTF-32LE"));
    }


    private void test(Charset charset) {
        String text = "Heloł Łorld";
        BOM bom = BOM.forCharset(charset);
        assertNotNull(bom);
        byte[] textWithBOM = bom.toBytesWithBOM(text);
        assertTrue(bom.matches(textWithBOM));
        assertEquals(bom, BOM.findMatching(textWithBOM));

        StringFromBytesBuilder sb = new StringFromBytesBuilder();
        sb.append(textWithBOM);
        assertEquals(text, sb.toString(charset));
        assertEquals(text, sb.toString());
    }
}
