package com.chapter23_reflection;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class UsageDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("src/com/chapter23_reflection/re.properties"));
        String classFullPath = properties.getProperty("classFullPath");
        String methodName = properties.getProperty("methodName");

        System.out.println("=====得到类对象的方法=====");
        Class cls = Class.forName(classFullPath);// 传入一个类的路径字符串，多用于配置文件
        System.out.println(cls);//class com.chapter23_reflection.Cat
        System.out.println(cls.getClass());//class java.lang.Class
        System.out.println(cls.getPackage().getName());//com.chapter23_reflection
        System.out.println(cls.getName());//com.chapter23_reflection.Cat
        //通过.class获取
        Class<Cat> cls2 = Cat.class;//多用于参数传递
        System.out.println(cls2);
        //通过一个对象.getClass()获取
        Cat myCat = new Cat();
        Class<? extends Cat> cls3 = myCat.getClass();
        System.out.println(cls3);
        //通过类加载器获得
        ClassLoader classLoader = myCat.getClass().getClassLoader();
        Class<?> cl4 = classLoader.loadClass(classFullPath);
        System.out.println(cl4);
        //基本类型
        Class<Integer> integerClass = int.class;
        Class<Character> characterClass = char.class;
        System.out.println(integerClass + " " + characterClass);
        //基本类型的包装可以直接通过.TYPE获得
        System.out.println(Integer.TYPE);

        //创建对象实例
        Cat cat = (Cat) cls.newInstance();
        //从这个实例拿到这个类的某个属性
        //Field name = cls.getField("name");
        //只能获得public的属性
        System.out.println("=====得到某个属性=====");
        Field ageField = cls.getField("age");
        System.out.println(ageField.get(cat));
        //通过反射给属性设定值
        System.out.println("=====设定某个属性=====");
        ageField.set(cat, 100);
        System.out.println(ageField.get(cat));
        //遍历这个类的所有字段
        System.out.println("=====得到所有属性=====");
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        System.out.println("=====调用某个方法=====");
        Method method = cls.getMethod(methodName);
        method.invoke(cat);


        Constructor constructor1 = cls.getConstructor();//括号可以指定构造器参数类型，现在返回无参构造器
        System.out.println(constructor1);
        Constructor constructor2 = cls.getConstructor(String.class);
        System.out.println(constructor2);


    }
}