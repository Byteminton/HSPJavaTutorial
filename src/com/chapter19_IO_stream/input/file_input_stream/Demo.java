package com.chapter19_IO_stream.input.file_input_stream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        byte[] buffer = new byte[8];
        int readLength = 0;
        try {
            fileInputStream = new FileInputStream("out/production/Practice/hello.txt");
            while ((readLength = fileInputStream.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, readLength));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
