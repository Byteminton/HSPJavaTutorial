package com.chapter22_qq.server.service;

import java.util.HashMap;
import java.util.Set;

public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    public static void setHm(HashMap<String, ServerConnectClientThread> hm) {
        ManageServerConnectClientThread.hm = hm;
    }

    public static void addServerConnectClientThread(String userId,
                                                    ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }
    public static void removeThread(String userId) {
        hm.remove(userId);
    }

    public static String getOnlineUserList() {
        Set<String> onlineUsers = hm.keySet();
        String list = "";
        for (String onlineUser : onlineUsers) {
            list += onlineUser + " ";
        }
        return list;
    }
}
