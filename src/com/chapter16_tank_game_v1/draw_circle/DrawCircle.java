package com.chapter16_tank_game_v1.draw_circle;

import javax.swing.*;
import java.awt.*;

public class DrawCircle extends JFrame { //JFrame 对应一个窗口，可以理解成一个画框
    private MyPanel mp;
    public static void main(String[] args) {
        new DrawCircle();
    }
    public DrawCircle() {
        mp = new MyPanel();
        //将画板放入画框中
        this.add(mp);
        this.setSize(400, 300);
        //将JFrame关闭时，程序将会自动停止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
//1.先定义一个MyPanel，继承JPanel类，在面板上画图
class MyPanel extends JPanel {

    //1. MyPanel是一个画板
    //2. Graphics g 是一个画笔
    //3. Graphics提供了很多绘图的方法
    @Override
    public void paint(Graphics g) {
        super.paint(g);// 调用父类的方法完成初始化
        g.drawOval(10, 10, 200, 140);
    }
}
