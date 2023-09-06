package com.chapter16_tank_game_v1.event_deal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Control the ball up, down, left and right by keyboard
 */
public class BallMove extends JFrame {
    private MyPanel mp;

    public static void main(String[] args) {
        new BallMove();
    }

    public BallMove() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(400, 300);
        // This frame can listen to keyboard event happened on mp
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel implements KeyListener {
    int x = 10;
    int y = 10;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);
        g.setColor(Color.CYAN);
        g.fillOval(x, y, 20, 20);
    }

    // It is called when there is character output
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // It is called when certain key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            y += 5;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            y -= 5;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x += 5;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x -= 5;
        }
        this.repaint();

    }

    // It is called when certain key is released
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
