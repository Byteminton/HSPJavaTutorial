package com.chapter21_network.homework.homework2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver {
    public static void main(String[] args) throws IOException {
        //用9999进行收发
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        datagramSocket.receive(packet);
        String content = new String(packet.getData(), 0, packet.getLength());

        String reply;
        if ("四大名著是哪些".equals(content)) {
            reply = "水浒传，西游记，三国演义，红楼梦";
        } else {
            reply = "what";
        }

        packet = new DatagramPacket(reply.getBytes(), 0, reply.getBytes().length
        , InetAddress.getLocalHost(), 9988);
        datagramSocket.send(packet);

        datagramSocket.close();
    }
}
