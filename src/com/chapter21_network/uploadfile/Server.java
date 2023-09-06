package com.chapter21_network.uploadfile;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server先等待Client传输一张图片，随后回复ACK，最后退出
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        //如果没有输入流，则会阻塞
        System.out.println("服务器启动，监听8888端口，等待数据传输...");
        Socket socket = serverSocket.accept();
        System.out.println("开始传输");
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);

        String dstPath = "src/熊猫.heic";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dstPath));
        bos.write(bytes);
        System.out.println("接收完成，回复ACK");


        BufferedWriter replyWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        replyWriter.write("图片已收到");
        replyWriter.flush();//把内容刷新到数据通道
        socket.shutdownOutput();// 由于不是newLine，对面无需reader，直接用inputStream即可接收


        replyWriter.close();
        bos.close();
        bis.close();
        socket.close();
        serverSocket.close();
    }
}
