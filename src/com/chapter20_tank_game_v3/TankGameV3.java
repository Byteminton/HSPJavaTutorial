package com.chapter20_tank_game_v3;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;

public class TankGameV3 extends JFrame {
    MyPanel mp;
    static Scanner scanner = new Scanner(System.in);
    int width = 1300;
    int height = 750;

    public static void main(String[] args) {
        new TankGameV3();
    }

    public TankGameV3() {
        if (new File(Record.getRecordFilePath()).exists() && !Record.checkFinished()) {
            boolean loop = true;
            do {
                System.out.print("An unfinished game has been detected. Do you want to continue? y/n ");
                String choice = scanner.next();
                if ("y".equals(choice) || "n".equals(choice)) {
                    loop = false;
                    mp = new MyPanel(choice);
                } else {
                    System.out.println("Invalid input. Please type in again.");
                }
            } while (loop);
        } else {
            mp = new MyPanel("n");
        }
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(width, height);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Record.keepRecord();
                System.exit(0);
            }
        });
    }
}
