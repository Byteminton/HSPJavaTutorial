package com.chapter20_tank_game_v3;

import java.awt.*;

public class Hero extends Tank {
    public final static int MOVE_SPEED = 20;
    public final static int BULLET_SPEED = 10;
    public final static Color HERO_COLOR = Color.CYAN;
    public Hero(int x, int y) {
        super(x, y);
        setColor(HERO_COLOR);
        setSpeed(MOVE_SPEED);
    }
    public Hero(int x, int y, int direction) {
        super(x, y, direction);
        setColor(HERO_COLOR);
        setSpeed(MOVE_SPEED);
    }
}
