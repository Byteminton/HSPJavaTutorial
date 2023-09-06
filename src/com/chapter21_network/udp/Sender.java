package com.chapter21_network.udp;

import java.io.IOException;
import java.net.*;

public class Sender {
    public static void main(String[] args) throws IOException {
        //创建DatagramSocket对象,准备以后在9988端口接收回复
        DatagramSocket datagramSocket = new DatagramSocket(9988);
        byte[] data = "hello,明天吃火锅".getBytes();
        DatagramPacket packet = new DatagramPacket(data, 0, data.length, InetAddress.getByName("10.249.40.74"), 9999);
        datagramSocket.send(packet);

        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        //用packet接收,数据会填充到packet里面，如果没有数据包发送过来，会在这里阻塞
        datagramSocket.receive(packet);
        //当接收到packet之后，需要拆包
        //length是实际的数据长度
        int length = packet.getLength();
        System.out.println(new String(packet.getData(), 0, length));
        datagramSocket.close();
    }
}
