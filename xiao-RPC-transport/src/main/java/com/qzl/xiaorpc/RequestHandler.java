package com.qzl.xiaorpc;

import java.io.InputStream;
import java.io.OutputStream;

public interface RequestHandler {
    void onRequest(InputStream recive, OutputStream response);
}
