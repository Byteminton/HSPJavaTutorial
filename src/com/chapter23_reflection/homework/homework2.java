package com.chapter23_reflection.homework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class homework2 {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.io.File");
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }
        Constructor<?> targetConstructor = cls.getDeclaredConstructor(String.class);
        Object file = targetConstructor.newInstance("/Users/corgi/Desktop/hello.txt");
        Method createNewFile = cls.getMethod("createNewFile");
        createNewFile.invoke(file);
    }
}
