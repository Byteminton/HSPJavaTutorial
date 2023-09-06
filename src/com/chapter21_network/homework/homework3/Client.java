package com.chapter21_network.homework.homework3;

import com.chapter21_network.uploadfile.StreamUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/corgi/Pictures/pap.er/Light.heic";
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(filePath);
        bw.newLine();
        bw.flush();
        System.out.println("Client发送文件名成功");


        byte[] pic = StreamUtils.streamToByteArray(socket.getInputStream());
        String dstPath = "src/Light.heic";
        new File(dstPath).createNewFile();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dstPath));
        bos.write(pic);
        System.out.println("Client接收文件成功");

        bw.close();
        bos.close();
        socket.close();


    }
}
