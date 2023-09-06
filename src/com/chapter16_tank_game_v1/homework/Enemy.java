package com.chapter16_tank_game_v1.homework;

public class Enemy extends Tank {
    public Enemy(int x, int y) {
        super(x, y);
        setType(1);
        setDirection(2);
    }
}
