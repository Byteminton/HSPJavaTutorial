package com.chapter18_tank_game_v2;

import java.awt.*;
import java.util.Vector;

public class Tank {
    private int x;//Horizontal coordinate of the tank
    private int y;//Vertical coordinate of the tank
    private int direction; // Direction of a tank 0-up, 1-right, 2-down, 3-left
    private int speed;// Moving speed
    private Color color;
    private Vector<Bullet> bullets = new Vector<>();
    private boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void shot(int speed) {
        Vector<Bullet> bullets = getBullets();
        Bullet bullet = null;
        switch (getDirection()) {
            case 0:
                bullet = new Bullet(getX() + 20, getY(), 0, speed);
                bullets.add(bullet);
                break;
            case 1:
                bullet = new Bullet(getX() + 60, getY() + 20, 1, speed);
                bullets.add(bullet);
                break;
            case 2:
                bullet = new Bullet(getX() + 20, getY() + 60, 2, speed);
                bullets.add(bullet);
                break;
            case 3:
                bullet = new Bullet(getX(), getY() + 20, 3, speed);
                bullets.add(bullet);
                break;
        }
        new Thread(bullet).start();
    }

}
