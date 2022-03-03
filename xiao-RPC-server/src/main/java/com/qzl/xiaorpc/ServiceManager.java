package com.qzl.xiaorpc;

import com.qzl.xiaorpc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServiceManager {
    private final Map<ServiceDescriptor, ServiceInstance> serviceMap;

    public ServiceManager() {
        serviceMap = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);

            ServiceDescriptor sd = ServiceDescriptor.from(interfaceClass, method);

            serviceMap.put(sd, serviceInstance);
            log.info("service reg {} {}", sd.getClazz(), sd.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        return serviceMap.get(request.getServiceDescriptor());
    }

}
