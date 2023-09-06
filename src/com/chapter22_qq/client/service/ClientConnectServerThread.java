package com.chapter22_qq.client.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //需要在后台一直与服务器进行通信
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject(); //如果服务器没有发送消息，线程会阻塞在这里
                if (message.getMessageType().equals(MessageType.MESSAGE_ONLINE_FRIEND)) {
                    String[] onlineUserList = message.getContent().split(" ");
                    System.out.println("\n==========当前在线用户列表==========");
                    for (String u : onlineUserList) {
                        System.out.println("用户: " + u);
                    }
                } else if (message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println("\n" + message.getSender() + ":" + message.getContent() +
                            "\t" + message.getSendTime());
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_TO_ALL)) {
                    System.out.println("\n" + message.getSender() + "(对所有人):" + message.getContent() +
                            "\t" + message.getSendTime());
                } else if (message.getMessageType().equals(MessageType.MESSAGE_FILE)) {
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n" + message.getSender() +
                            "向您传送了文件" + message.getFileName() + "，已保存在" + message.getDest());
                } else {
                    System.out.println("其他类型信息");
                }
            } catch (Exception e) {
                System.out.println(e);
                //throw new RuntimeException(e);
            }

        }
    }
}
