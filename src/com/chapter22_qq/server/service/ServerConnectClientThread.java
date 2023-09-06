package com.chapter22_qq.server.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * 该类的某个对象和某个客户端的某个socket通信
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void ACK(Message message, int type) throws IOException {
        // type-1, reply to text message; type-2, reply to file message
        Message reply = new Message();
        reply.setMessageType(MessageType.MESSAGE_COMM_MES);
        reply.setSendTime(message.getSendTime());
        reply.setSender("服务器");
        switch (type) {
            case 1:
                reply.setContent("消息发送成功");
                System.out.println(message.getSender() + "给" + message.getReceiver() + "发送了一条信息"
                        +"\t" + message.getSendTime());
                break;
            case 2:
                reply.setContent("文件上传成功");
                System.out.println(message.getSender() + "给" + message.getReceiver() + "发送了一个文件:"
                        + message.getFileName() + "\t" + message.getSendTime());
                break;
            case 3:
                reply.setContent("开始传送离线信息");
                System.out.println(message.getSender() + "请求离线信息" + "\t" + message.getSendTime());
                break;
            case 4:
                reply.setContent("离线信息发送完毕");
                System.out.println(message.getSender() + "离线信息发送完毕" + "\t" + new Date().toString());
                break;
            case 5:
                reply.setContent(message.getReceiver() + "处于离线状态，已保存为离线消息/文件");
                System.out.println(message.getSender() + "给" + message.getReceiver() +
                        "发送了一条离线消息/文件" + "\t" + message.getSendTime());
                break;
            default:
                System.out.println("ACK error");
        }

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(reply);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("服务器和客户端" + userId + "保持连接中...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println(message.getSender() + "请求在线用户列表");
                    String onlineUserList = ManageServerConnectClientThread.getOnlineUserList();
                    Message reply = new Message();
                    reply.setMessageType(MessageType.MESSAGE_ONLINE_FRIEND);
                    reply.setContent(onlineUserList);
                    reply.setReceiver(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(reply);
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    ManageServerConnectClientThread.removeThread(message.getSender());
                    socket.close();
                    System.out.println(message.getSender() + "退出");
                    break;
                } else if (message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    ACK(message, 1);
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getReceiver());
                    //离线状态
                    if (serverConnectClientThread == null) {
                        OfflineMessageFiles.addMessage(message.getReceiver(), message);
                        ACK(message, 5);
                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream(
                                serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_TO_ALL)) {
                    HashMap<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();
                    Set<String> onlineUsers = hm.keySet();
                    for (String onlineUser : onlineUsers) {
                        if (onlineUser.equals(message.getSender())) {
                            ACK(message, 1);
                        } else {
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUser).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getMessageType().equals(MessageType.MESSAGE_FILE)) {
                    ACK(message, 2);
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getReceiver());
                    //离线状态
                    if (serverConnectClientThread == null) {
                        OfflineMessageFiles.addMessage(message.getReceiver(), message);
                        ACK(message, 5);
                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream(
                                serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                    }
                } else if (message.getMessageType().equals(MessageType.MESSAGE_OFFLINE_REQUEST)) {
                    ACK(message, 3);
                    int size = OfflineMessageFiles.messageSize(message.getSender());
                    if (size > 0 ) {
                        for (int i = 0; i < size; i++) {
                            //这里每次都要用一个新的oos流
                            ObjectOutputStream oos = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(
                                    message.getSender()).getSocket().getOutputStream());
                            oos.writeObject(OfflineMessageFiles.getMessage(message.getSender(), i));
                        }
                        OfflineMessageFiles.clearMessage(message.getSender());
                    }
                    ACK(message, 4);
                } else {
                    System.out.println("其他类型信息");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
