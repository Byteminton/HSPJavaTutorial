package com.chapter08.q1;

public class Main {
    public static void main(String[] args) {
        Main instance = new Main();
        Person[] people = {new Person("Jack", 25, "manager"),
                new Person("Mary", 31, "staff"),
                new Person("Bob", 70, "staff")};

        instance.sort(people);
        for (int i = 0; i < people.length; i++) {
            System.out.println(people[i]);
        }
    }

    public void sort(Person[] people) {
        Person temp;
        for (int i = 0; i < people.length - 1; i++) {
            for (int j = 0; j < people.length - 1 - i; j++) {
                if (compare(people[j], people[j + 1]) > 0) {
                    temp = people[j];
                    people[j] = people[j + 1];
                    people[j + 1] = temp;
                }
            }
        }
    }

    public int compare(Person p1, Person p2) {
        if (p1.getAge() < p2.getAge()) {
            return 1;
        } else if (p1.getAge() == p2.getAge()) {
            return 0;
        }
        return -1;
    }
}

