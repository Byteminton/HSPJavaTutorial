package com.houserent.service;

import com.houserent.domain.House;

public class HouseService {
    private House[] houses;
    private int houseNums;
    private int idCounter = 1;

    public HouseService(int size) {
        houses = new House[size];
    }

    public House[] list() {
        return houses;
    }

    public boolean add(House newHouse) {
        if (houseNums == houses.length) {
            System.out.println("列表已满，无法继续添加");
            return false;
        }
        houses[houseNums++] = newHouse;
        newHouse.setId(idCounter++);
        return true;
    }

    public boolean del(int delId) {
        int index = -1;
        for (int i = 0; i < houseNums; i++) {
            if (delId == houses[i].getId()) {
                index = i;
            }
        }
        if (index == -1) {
            return false;
        }
        // 找到删除的房屋后，将后面的房屋移到前面来
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
        houses[--houseNums] = null;
        return true;
    }

    public House findById(int findId) {
        for (int i = 0; i < houseNums; i++) {
            if (findId == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;
    }
}
