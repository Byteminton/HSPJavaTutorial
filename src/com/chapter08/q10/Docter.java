package com.chapter08.q10;

public class Docter {
    private String name;
    private int age;
    private String job;
    private char gender;
    private int sal;

    public Docter(String name, int age, String job, char gender, int sal) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.gender = gender;
        this.sal = sal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Docter)) {
            return false;
        }

        Docter doc = (Docter) obj;
        return name.equals(doc.getName()) && age == doc.getAge() &&
                job.equals(doc.getJob()) && gender == doc.getGender() &&
                sal == doc.getGender();
    }
}
