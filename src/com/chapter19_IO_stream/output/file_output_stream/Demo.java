package com.chapter19_IO_stream.output.file_output_stream;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) {

    }

    /**
     * 如何写入文件
     * 如果该文件不存在，则会自动创建
     */
    @Test
    public void writeFile() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("out/production/Practice/hello.txt");
            // true 表示追加模式
            //fileOutputStream = new FileOutputStream("out/production/Practice/hello.txt", true);
            //fileOutputStream.write('a');
            //fileOutputStream.write(new String("hello, world 王敬松").getBytes());
            // 从0字节位置开始，写入5个字节
            fileOutputStream.write(new String("hello, world 王敬松").getBytes(), 0, 5);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void copyFile() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        byte[] buffer = new byte[1024];
        int readLength;
        try {
            fileInputStream = new FileInputStream("/Users/corgi/Documents/ComputerScience/" +
                    "韩顺平 2021零基础学Java 【软件 资料 代码 笔记】/资料/分享资料/111.wav");
            fileOutputStream = new FileOutputStream("out/production/Practice/music.wav");
            while ((readLength = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, readLength);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
