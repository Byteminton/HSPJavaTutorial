package com.chapter22_qq.client.service;

import com.chapter22_qq.Message;
import com.chapter22_qq.MessageType;
import com.chapter22_qq.utility.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class FileClientService {
    public void sendFile(String sender) {
        System.out.print("请输入账号Id(最大长度为100个字符):");
        String receiver = Utility.readString(100);
        System.out.println("请输入文件路径(最大长度为100个字符):");
        String src = Utility.readString(100);
        String[] split = src.split("/");
        String fileName = split[split.length - 1];
        String dst = "src/" + fileName;
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_FILE);
        message.setFileName(fileName);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSrc(src);
        message.setDest(dst);
        message.setSendTime(new Date().toString());

        byte[] fileBytes = new byte[(int) new File(src).length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);
            message.setFileBytes(fileBytes);
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
