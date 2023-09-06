package com.smallchange.oop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 完成零钱通各个功能的类
 */
public class SmallChangeSysOOP {
    boolean loop = true;
    Scanner scanner = new Scanner(System.in);
    String key = "";
    String details = "==========零钱通明细==========";
    double money = 0;
    double balance = 0;
    Date date = null; // java.util.Date类型，表示一个日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //用于日期格式化
    String note = "";

    public void mainMenu() {
        do {
            System.out.println("\n==========零钱通菜单==========");
            System.out.println("\t\t1 零钱通明细");
            System.out.println("\t\t2 收益入账");
            System.out.println("\t\t3 消费");
            System.out.println("\t\t4 退出");

            System.out.print("请选择(1-4): ");
            key = scanner.next();

            switch (key) {
                case "1": details();
                case "2": income();
                case "3": pay();
                case "4": exit();
                default: System.out.println("选择有误，请重新选择");
            }

        } while (loop);

        System.out.println("==========退出零钱通项目==========");

    }

    public void details() {
        System.out.println(details);
    }

    public void income() {
        System.out.print("收益入账金额:");
        money = scanner.nextDouble();

        // 校验所有不合法的条件
        if (money <= 0) {
            System.out.println("收益入账金额需要大于0");
            return;
        }

        balance += money;
        date = new Date(); //获取当前日期
        details += "\n收益入账\t+" + money + "\t" +
                sdf.format(date) + "\t余额:" + balance;
    }

    public void pay() {
        System.out.print("消费金额:");
        money = scanner.nextDouble();

        if (money <= 0 || money > balance) {
            System.out.println("消费金额应该在0-" + balance + "之间");
            return;
        }

        System.out.print("消费说明:");
        note = scanner.next();
        balance -= money;
        details += "\n" + note + "\t-" + money + "\t" +
                sdf.format(date) + "\t余额:" + balance;
    }

    public void exit() {
        String choice = "";
        while (true) {
            System.out.println("你确定要退出吗? y/n");
            choice = scanner.next();
            if ("y".equals(choice) || "n".equals(choice)) {
                break;
            }
        }
        if ("y".equals(choice)) {
            loop = false;
        }
    }
}
