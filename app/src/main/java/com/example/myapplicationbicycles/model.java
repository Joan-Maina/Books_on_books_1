package com.example.myapplicationbicycles;

public class model {
    private  int number;
    private String name;

    public model() {
    }

    public model(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }
}

