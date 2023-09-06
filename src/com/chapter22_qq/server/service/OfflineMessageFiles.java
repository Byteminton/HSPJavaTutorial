package com.chapter22_qq.server.service;

import com.chapter22_qq.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OfflineMessageFiles {
    private static ConcurrentHashMap<String, List<Message>> offline = new ConcurrentHashMap<>();

    public static void addMessage(String userId, Message message) {
        if (offline.get(userId) == null) {
            offline.put(userId, new ArrayList<>());
        }
        offline.get(userId).add(message);
    }
    public static Message getMessage(String userId, int index) {
        return offline.get(userId).get(index);
    }

    public static int messageSize(String userId) {
        if (offline.get(userId) == null) {
            return 0;
        }
        return offline.get(userId).size();
    }
    public static void clearMessage(String userId) {
        offline.get(userId).clear();
    }
}
