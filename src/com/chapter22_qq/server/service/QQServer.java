package com.chapter22_qq.server.service;
/**
 * 服务端，监听 9999 端口，等待客户端的连接，并等待通信
 */

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;
import com.chapter22_qq.User;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class QQServer {
    private ServerSocket serverSocket;
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("林丹", new User("林丹", "123456"));
        validUsers.put("李宗伟", new User("李宗伟", "123456"));
        validUsers.put("石宇奇", new User("石宇奇", "123456"));
    }

    private boolean checkUser(String userId, String password) {
        User user = validUsers.get(userId);
        if (user == null) {
            return false;
        } else if (!user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
    public QQServer() {
        try {
            System.out.println("服务器在9999端口监听....");
            new Thread(new SendPublicNews()).start();
            //端口号可以写在配置文件
            Properties properties = new Properties();
            properties.load(new FileReader("src/qq.properties"));
            int port = Integer.parseInt(properties.getProperty("port"));
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();
                Message message = new Message();
                if (checkUser(user.getUserID(), user.getPassword())) {
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getUserID());
                    serverConnectClientThread.start();
                    ManageServerConnectClientThread.addServerConnectClientThread(user.getUserID(),
                            serverConnectClientThread);
                } else {
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                    //登陆失败了关闭socket
                    socket.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
