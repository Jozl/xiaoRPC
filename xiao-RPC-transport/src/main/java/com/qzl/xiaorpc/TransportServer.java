package com.qzl.xiaorpc;

public interface TransportServer {
    void init(int port, RequestHandler requestHandler);
    void start();

    void close();
}
