package com.chapter22_qq.client.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;
import com.chapter22_qq.utility.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    public void privateChat(String sender) throws IOException {
        System.out.print("请输入账号Id(最大长度为100个字符):");
        String receiver = Utility.readString(100);
        System.out.println("请输入正文(最大长度为100个字符):");
        String content = Utility.readString(100);
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(sender);
        ObjectOutputStream oos = new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public void publicChat(String sender) throws IOException {
        System.out.println("请输入正文(最大长度为100个字符):");
        String content = Utility.readString(100);
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver("所有人");
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMessageType(MessageType.MESSAGE_CLIENT_TO_ALL);
        ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(sender);
        ObjectOutputStream oos = new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
