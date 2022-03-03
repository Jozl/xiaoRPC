package com.qzl.xiaorpc;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean("asd", 18);

        byte[] encoded = encoder.encode(bean);
        assertNotNull(encoded);

        Decoder decoder = new JSONDecoder();
        assertEquals(bean.getName(), decoder.decode(encoded, TestBean.class).getName());
    }
}