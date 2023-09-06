package com.chapter22_qq;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登陆成功
    String MESSAGE_LOGIN_FAIL = "2";//表示登陆失败
    String MESSAGE_COMM_MES = "3";//普通信息包
    String MESSAGE_GET_ONLINE_FRIEND = "4";//要求返回在线用户列表
    String MESSAGE_ONLINE_FRIEND = "5";//在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
    String MESSAGE_CLIENT_TO_ALL = "7";//群发消息
    String MESSAGE_FILE = "8";//文件
    String MESSAGE_OFFLINE_REQUEST = "9";//请求离线消息和文件
}
