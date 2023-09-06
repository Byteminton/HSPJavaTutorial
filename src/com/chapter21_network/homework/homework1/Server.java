package com.chapter21_network.homework.homework1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String content = br.readLine();
        String reply;
        if ("name".equals(content)) {
            reply = "我是Wang Jingsong";
        } else if ("hobby".equals(content)) {
            reply = "编写java程序";
        } else {
            reply = "你说啥呢";
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(reply);
        bw.newLine();
        bw.flush();

        br.close();
        bw.close();
        socket.close();
    }
}
