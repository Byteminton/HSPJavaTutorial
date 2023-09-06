package com.chapter19_IO_stream.input.buffered_reader;

import java.io.BufferedReader;
import java.io.FileReader;

public class Demo {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("out/production/Practice/hello.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
    }
}
