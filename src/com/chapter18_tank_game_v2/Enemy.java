package com.chapter18_tank_game_v2;

import java.awt.*;

public class Enemy extends Tank implements Runnable {

    public final static int MOVE_SPEED = 10;
    public final static int BULLET_SPEED = 5;
    public final static Color ENEMY_COLOR = Color.YELLOW;
    public Enemy(int x, int y) {
        super(x, y);
        setColor(ENEMY_COLOR);
        setSpeed(MOVE_SPEED);
        setDirection(2);
    }

    public void enemyMove() {
        switch (getDirection()) {
            case 0:
                moveUp();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
            }
    }

    public void checkTurnAround() {
        if (getDirection() == 0 && getY() <= 0) {
            setDirection(2);
        } else if (getDirection() == 1 && getX() >= 1000 - 60) {
            setDirection(3);
        } else if (getDirection() == 2 && getY() >= 750 - 60) {
            setDirection(0);
        } else if (getDirection() == 3 && getX() <= 0) {
            setDirection(1);
        }
    }

    @Override
    public void run() {
        int count = 0;
        while (isLive()) {
            checkTurnAround();
            enemyMove();
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (count == 10) {
                setDirection((int) (Math.random() * 4));
                shot(BULLET_SPEED);
                count = 0;
            }
        }
    }
}
