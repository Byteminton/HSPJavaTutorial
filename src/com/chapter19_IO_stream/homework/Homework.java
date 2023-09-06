package com.chapter19_IO_stream.homework;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class Homework {
    public static void main(String[] args) {

    }

    @Test
    public void homework1() throws IOException {
        String parent = "out/production/Practice/mytemp";
        File directory = new File(parent);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(parent, "/hello.txt");
        if(file.exists()) {
            System.out.println("hello.txt已经存在,请不要重复创建");
        } else {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("hello,world~".getBytes());
            fileOutputStream.close();
        }
    }

    @Test
    public void homework2() throws IOException {
        String src = "out/production/Practice/hello.txt";
        int row = 1;
        String line;
        BufferedReader br = new BufferedReader(new FileReader(src));
        while ((line = br.readLine()) != null) {
            System.out.println(row++ + " " + line);
        }
        br.close();
    }

    @Test
    public void homework3() throws IOException {
        String path = "out/production/Practice/dog.properties";
        Properties properties = new Properties();
        properties.setProperty("name", "tom");
        properties.setProperty("age", "5");
        properties.setProperty("color", "red");
        properties.store(new FileWriter(path), null);

        Dog dog = new Dog(properties.getProperty("name"),
                Integer.parseInt(properties.getProperty("age")),
                        properties.getProperty("color"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("out/production/Practice/dog.dat"));
        objectOutputStream.writeObject(dog);
        objectOutputStream.close();
    }
}
class Dog implements Serializable{
    String name;
    int age;
    String color;

    public Dog(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }
}
