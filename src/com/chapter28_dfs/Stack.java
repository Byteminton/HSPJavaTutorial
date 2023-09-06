package com.chapter28_dfs;

public class Stack {
    private Coordinate[] stack;
    private int top;
    private int maxSize;

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new Coordinate[maxSize];
        this.top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public void push(Coordinate coordinate) {
        stack[++top] = coordinate;
    }

    public Coordinate pop() {
        return stack[top--];
    }

    public Coordinate peek() {
        return stack[top];
    }

}
