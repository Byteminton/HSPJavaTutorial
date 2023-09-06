package com.chapter22_qq.client;

import com.chapter22_qq.client.service.FileClientService;
import com.chapter22_qq.client.service.MessageClientService;
import com.chapter22_qq.client.service.UserClientService;
import com.chapter22_qq.utility.Utility;

import java.io.IOException;

public class QQView {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQView().mainMenu();
        System.out.println("WJS通信系统客户端退出...");
        System.exit(0);
    }
    private boolean loop = true;
    private String key;//接收用户的键盘输入
    private UserClientService userClientService = new UserClientService();//用于登陆服务器和注册用户
    private MessageClientService messageClientService = new MessageClientService();//用于私聊和群聊
    private FileClientService fileClientService = new FileClientService();//用于私聊和群聊
    //显示主菜单
    public void mainMenu() throws IOException, ClassNotFoundException {
        while (loop) {
            System.out.println("==========欢迎登陆WJS通信系统==========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择:");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入用户号:");
                    String userId = Utility.readString(50); //最多50位
                    System.out.print("请输入密码:");
                    String pwd = Utility.readString(50);
                    //在服务端验证该用户是否合法
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎 (用户 " + userId + ")登陆成功==========");
                        userClientService.requestOfflineMessage();
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单(用户  " + userId + ") ==========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择:");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.requestOnlineFriendList();
                                    sleep(100);
                                    break;
                                case "2":
                                    messageClientService.publicChat(userId);
                                    sleep(100);
                                    break;
                                case "3":
                                    messageClientService.privateChat(userId);
                                    sleep(100);
                                    break;
                                case "4":
                                    fileClientService.sendFile(userId);
                                    sleep(100);
                                    break;
                                case "9":
                                    userClientService.requestExit();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("==========登陆失败==========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;

            }
        }
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
