package com.chapter19_IO_stream.input.file_reader;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) {

    }
    @Test
    public void reader() {
        FileReader fileReader = null;
        char[] buffer = new char[8];
        int readLen;
        try {
            fileReader = new FileReader("out/production/Practice/hello.txt");
            while ((readLen = fileReader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, readLen));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
