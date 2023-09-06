package com.chapter16_tank_game_v1.tank_game_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyPanel extends JPanel implements KeyListener {
    Hero hero;

    public MyPanel() {
        hero = new Hero(100, 100);//initial own tank
        hero.setSpeed(20);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//default black
        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);

    }

    /**
     * @param x      // x coordinate of the upper left corner of a tank
     * @param y      // y coordinate of the upper left corner of a tank
     * @param g      // Paint Brush
     * @param direct // Up, down, left or right
     * @param type   // Type of a tank(Hero or Enemy)
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: // Hero
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        // According to tank's direction, paint corresponding tanks
        switch (direct) {
            case 0: // up
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // body
                g.fillOval(x + 10, y + 20, 20, 20); // battery
                g.drawLine(x + 20, y, x + 20, y + 30); // cannon barrel
                break;
            case 1: // right
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // body
                g.fillOval(x + 20, y + 10, 20, 20); // battery
                g.drawLine(x + 60, y + 20, x + 30, y + 20); // cannon barrel
                break;
            case 2: // down
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // body
                g.fillOval(x + 10, y + 20, 20, 20); // battery
                g.drawLine(x + 20, y + 60, x + 20, y + 30); // cannon barrel
                break;
            case 3: // left
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // body
                g.fillOval(x + 20, y + 10, 20, 20); // battery
                g.drawLine(x, y + 20, x + 30, y + 20); // cannon barrel
                break;
            default:
                System.out.println("No treatment for now...");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            hero.moveLeft();
        }
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
