package com.chapter20_tank_game_v3;

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

    public Enemy(int x, int y, int direction) {
        super(x, y, direction);
        setColor(ENEMY_COLOR);
        setSpeed(MOVE_SPEED);
    }

    public void checkTurnAround() {
        if ((touchUpperBoundary() && getDirection() == 0) ||
                (touchLowerBoundary() && getDirection() == 2) ||
                (touchLeftBoundary() && getDirection() == 3) ||
                touchRightBoundary() && getDirection() == 1) {
            turnAround();
        }
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
            default:
                System.out.println("An error occurs while enemies moving");
        }
    }

    @Override
    public void run() {
        int count = 0;
        while (isLive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkTurnAround();
            enemyMove();
            count++;
            if (count == 10) {
                setDirection((int) (Math.random() * 4));
                shot(BULLET_SPEED);
                count = 0;
            }
        }
    }
}
