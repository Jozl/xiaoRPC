package com.qzl.xiaorpc;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    public static ServiceDescriptor from(Class clazz, Method method) {
        return new ServiceDescriptor(
                clazz.getName(), method.getName()
                , method.getReturnType().getName()
                , Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).toArray(String[]::new));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDescriptor that = (ServiceDescriptor) o;
        return Objects.equals(clazz, that.clazz) &&
                Objects.equals(method, that.method) &&
                Objects.equals(returnType, that.returnType) &&
                Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public String toString() {
        return "clazz = " + clazz
                + ",method = " + method
                + ",returnType = " + returnType
                + ",parameterTypes = " + Arrays.toString(parameterTypes);
    }
}
