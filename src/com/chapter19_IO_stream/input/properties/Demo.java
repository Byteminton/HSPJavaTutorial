package com.chapter19_IO_stream.input.properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Demo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("out/production/Practice/mysql.properties"));
        properties.list(System.out);
        String usr = properties.getProperty("usr");
        String sch = properties.getProperty("sch");
        System.out.println(usr + " " + sch);
        properties.setProperty("track", "General");
        // null 表示没有注释
        properties.store(new FileWriter("out/production/Practice/mysql.properties"), null);
    }
}
