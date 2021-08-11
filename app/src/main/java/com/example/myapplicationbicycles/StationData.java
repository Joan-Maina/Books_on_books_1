package com.example.myapplicationbicycles;

public class StationData {
    String name;
    Long number;

    public StationData() {
    }

    public StationData(String name, Long number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
