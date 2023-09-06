package com.chapter23_reflection.homework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class homework1 {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.chapter23_reflection.homework.PrivateTest");
        Object test = cls.newInstance();
        Field name = cls.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(test));
        name.set(test, "HelloWorld");
        Method getName = cls.getMethod("getName");
        System.out.println(getName.invoke(test));

    }
}
class PrivateTest {
    private String name = "HelloKitty";

    public String getName() {
        return name;
    }
}
