package com.chapter19_IO_stream.input.object_input_stream;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Demo {
    public static void main(String[] args) throws Exception {
        String path = "out/production/Practice/myRecord.dat";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        // 读取数据的顺序必须和写入的顺序一致
        System.out.println(ois.readInt());
        System.out.println(ois.readObject().getClass());
        ois.close();
    }

}
