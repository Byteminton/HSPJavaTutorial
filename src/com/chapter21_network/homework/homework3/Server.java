package com.chapter21_network.homework.homework3;

import com.chapter21_network.uploadfile.StreamUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String filePath = br.readLine();
        if (!new File(filePath).exists()) {
            filePath = "/Users/corgi/Pictures/pap.er/叶子.jpg";
        }
        System.out.println("Server接收文件名成功");

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        byte[] pic = StreamUtils.streamToByteArray(bis);
        socket.getOutputStream().write(pic);
        socket.shutdownOutput();
        System.out.println("Server发送文件成功");

        br.close();
        bis.close();
        socket.close();
    }
}
