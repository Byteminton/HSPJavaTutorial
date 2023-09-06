package com.chapter21_network.uploadfile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client先给Server发送一张图片，接收到ACK后退出
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        System.out.println("开始上传");
        String srcPath = "/Users/corgi/Pictures/pap.er/熊猫.heic";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcPath));
        //图片字节数组
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //通过socket获得的输出流，包装成BufferedOutputStream，便于操作
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        socket.shutdownOutput();// 设置文件结束的标记，不然服务器(接受图片端不知何时结束)
        System.out.println("传输完成");
        // 直接将一个流的内容转换成了String
        System.out.println("收到服务器的回复:" + StreamUtils.streamToString(socket.getInputStream()));

        bis.close();
        bos.close();
        socket.close();
    }
}
