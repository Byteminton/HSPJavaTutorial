package com.chapter19_IO_stream.output.file_writer;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) {

    }

    @Test
    public void reader() {
        FileWriter fileWriter = null;
        char[] buffer = new char[]{'a', 'b', 'c'};
        int readLen;
        try {
            fileWriter = new FileWriter("out/production/Practice/hello.txt");
            //fileWriter.write('1');
            //fileWriter.write(buffer);
            //fileWriter.write("wjs".toCharArray(), 0, 3);
            //fileWriter.write("wjs");
            //fileWriter.write("wjs",offset, length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
