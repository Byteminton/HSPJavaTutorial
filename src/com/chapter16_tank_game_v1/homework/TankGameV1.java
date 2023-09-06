package com.chapter16_tank_game_v1.homework;

import javax.swing.*;

public class TankGameV1 extends JFrame {
    MyPanel mp;
    public static void main(String[] args) {
        new TankGameV1();
    }

    public TankGameV1() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
