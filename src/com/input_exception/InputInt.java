package com.input_exception;

import java.util.Scanner;

public class InputInt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num;
        while (true) {
            System.out.print("请输入一个整数:");
            try {
                num = Integer.parseInt(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("输入有误，请重新输入");;
            }
        }
        System.out.println("num = " + num);
    }
}
