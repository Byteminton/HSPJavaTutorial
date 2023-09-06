package com.chapter19_IO_stream.output.object_output_stream;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Demo {
    public static void main(String[] args) throws Exception {
        // 序列化后文件名后缀是随意的
        String path = "out/production/Practice/data.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        // 写入的对象必须实现Serializable接口
        oos.writeInt(100);
        oos.writeBoolean(true);
        oos.writeChar('a');
        oos.writeDouble(1.25);
        oos.writeUTF("王敬松");
        oos.writeObject(new Dog("Corgi", 10));
        oos.close();

    }
}
class Dog implements Serializable {
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
