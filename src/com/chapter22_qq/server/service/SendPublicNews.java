package com.chapter22_qq.server.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;
import com.houserent.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class SendPublicNews implements Runnable {
    private Scanner sc = new Scanner(System.in);
    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要推送的消息[输入exit退出推送服务]");
            String news = Utility.readString(1000);
            if (news.equals("exit")) {
                break;
            }
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE_CLIENT_TO_ALL);
            message.setSender("服务器");
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("推送成功");

            HashMap<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();
            Set<String> onlineUsers = hm.keySet();
            for (String onlineUser : onlineUsers) {
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(hm.get(onlineUser).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
