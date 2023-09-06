package com.chapter22_qq.client.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;
import com.chapter22_qq.User;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;

/**
 * 完成用户登陆验证和用户注册等功能
 */
public class UserClientService {
    private User user = new User();
    private Socket socket;
    public boolean checkUser(String userId, String pwd) throws IOException, ClassNotFoundException {
        boolean isValid = false;
        user.setUserID(userId);
        user.setPassword(pwd);
        //将user发送到 9999 端口，服务器会监听 9999 端口
        Properties properties = new Properties();
        properties.load(new FileReader("src/qq.properties"));
        String serverIP = properties.getProperty("serverIP");
        int port = Integer.parseInt(properties.getProperty("port"));
        socket = new Socket(serverIP, port);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(user);

        //读取服务器回复的Message对象
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();
        if (message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
            //创建一个和服务器端保持通信的线程 ClientConnectServerThread
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
            clientConnectServerThread.start();
            ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
            isValid = true;
        } else {
            //如果登陆失败，则没有启动线程，但socket已经创建，需要关闭socket
            socket.close();
        }
        return isValid;
    }

    public void requestOnlineFriendList() throws IOException {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserID());
        ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(user.getUserID());
        ObjectOutputStream oos = new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public void requestExit() throws IOException {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getUserID());
        ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(user.getUserID());
        ObjectOutputStream oos = new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public void requestOfflineMessage() throws IOException {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_OFFLINE_REQUEST);
        message.setSender(user.getUserID());
        message.setSendTime(new Date().toString());
        ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(user.getUserID());
        ObjectOutputStream oos = new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
        oos.writeObject(message);
    }
}
