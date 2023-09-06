package com.chapter16_tank_game_v1.graphics_method_demo;

import javax.swing.*;
import java.awt.*;

public class GraphicsMethodDemo extends JFrame {
    private MyPanel mp;
    public static void main(String[] args) {
        new GraphicsMethodDemo();

    }
    public GraphicsMethodDemo() {
        mp = new MyPanel();
        //将画板放入画框中
        this.add(mp);
        this.setSize(400, 300);
        //将JFrame关闭时，程序将会自动停止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel {

    //1. MyPanel是一个画板
    //2. Graphics g 是一个画笔
    //3. Graphics提供了很多绘图的方法
    @Override
    public void paint(Graphics g) {
        super.paint(g);// 调用父类的方法完成初始化

        //Draw image
        //1. Get image from file
        //2. Put the image under Project root folder
        //3. '/' means relevant path
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/leave.jpg"));
        g.drawImage(image, 10,10,300,200,this);

        //Oval
        g.drawOval(10, 10, 200, 140);
        //Line
        g.setColor(Color.pink);
        g.drawLine(10,10,200,200);
        //Rectangular
        g.drawRect(10,10,200,200);
        //set color first
        g.setColor(Color.yellow);
        //Color-filled rectangular
        g.fillRect(200,200,50,50);
        g.setColor(Color.red);
        //Color-filled oval
        g.fillOval(10,10,50,50);

        //Draw string
        //Set font
        g.setFont(new Font("隶书", Font.BOLD, 50));
        //Start point is the lower left corner
        g.drawString("北京你好",100,100);

    }
}

