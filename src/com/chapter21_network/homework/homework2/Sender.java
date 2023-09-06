package com.chapter21_network.homework.homework2;

import java.io.IOException;
import java.net.*;

public class Sender {
    public static void main(String[] args) throws IOException {
        String content = "四大名著是哪些";
        //用9988进行收发
        DatagramSocket datagramSocket = new DatagramSocket(9988);
        DatagramPacket packet = new DatagramPacket(content.getBytes(), 0, content.getBytes().length
                , InetAddress.getLocalHost(), 9999);
        datagramSocket.send(packet);

        byte[] data = new byte[1024];
        packet = new DatagramPacket(data, data.length);
        datagramSocket.receive(packet);
        System.out.println(new String(packet.getData(), 0, packet.getLength()));

        datagramSocket.close();
    }
}
