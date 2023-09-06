package com.chapter23_reflection;

public class Cat {
    private String name = "Meow";
    public int age = 18;
    public String color;

    public Cat() {

    }
    public Cat(String name) {
        this.name = name;
    }

    public void hi() {
        System.out.println("hi");
    }
}
