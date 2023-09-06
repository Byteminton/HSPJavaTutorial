package com.chapter19_IO_stream.output.buffered_writer;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Demo {
    public static void main(String[] args) throws Exception {
        // FileWriter 里 true 是追加
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out/production/Practice/hello.txt"));
        bufferedWriter.write("hello world");
        bufferedWriter.newLine();
        bufferedWriter.write("hello Beijing");
        bufferedWriter.close();
    }
}
