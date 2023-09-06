package com.chapter18_tank_game_v2;

import javax.swing.*;

public class TankGameV2 extends JFrame {
    MyPanel mp;
    int width = 1000;
    int height = 750;

    public static void main(String[] args) {
        new TankGameV2();
    }

    public TankGameV2() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(width, height);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
