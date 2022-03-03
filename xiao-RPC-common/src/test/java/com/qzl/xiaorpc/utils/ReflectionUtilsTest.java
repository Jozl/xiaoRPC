package com.qzl.xiaorpc.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        Assert.assertNotNull(testClass);
    }

    @Test
    public void getPublicMethods() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        for (Method publicMethod : publicMethods) {
            System.out.println(publicMethod.getName());

        }
        Assert.assertEquals(2, publicMethods.length);
        Assert.assertEquals("b", publicMethods[0].getName());
    }

    @Test
    public void invoke() {
        Object invoke = ReflectionUtils.invoke(new TestClass(), ReflectionUtils.getPublicMethods(TestClass.class)[0]);
        Assert.assertEquals("bb", invoke);
    }
}
