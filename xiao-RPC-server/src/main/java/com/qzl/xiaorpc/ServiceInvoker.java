package com.qzl.xiaorpc;

import com.qzl.xiaorpc.utils.ReflectionUtils;

public class ServiceInvoker {
    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(), request.getParameters());
    }
}
