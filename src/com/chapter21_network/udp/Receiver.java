package com.chapter21_network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver {
    public static void main(String[] args) throws IOException {
        //创建一个DatagramSocket对象，准备在9999接收数据
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        //创建一个DatagramPacket对象，准备接收数据
        //UDP数据每个包最大是64kB
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        //用packet接收,数据会填充到packet里面，如果没有数据包发送过来，会在这里阻塞
        datagramSocket.receive(packet);
        //当接收到packet之后，需要拆包
        //length是实际的数据长度
        int length = packet.getLength();
        byte[] data = packet.getData();
        System.out.println(new String(data, 0, length));


        byte[] reply = "好的，明天见".getBytes();
        packet = new DatagramPacket(reply, 0, reply.length, InetAddress.getByName("10.249.40.74"), 9988);
        datagramSocket.send(packet);

        datagramSocket.close();
    }
}
