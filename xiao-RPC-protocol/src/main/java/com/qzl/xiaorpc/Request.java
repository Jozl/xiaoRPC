package com.qzl.xiaorpc;

import lombok.Data;

/**
 * RPC请求
 */


@Data
public class Request {
    private ServiceDescriptor serviceDescriptor;
    private Object[] parameters;
}
