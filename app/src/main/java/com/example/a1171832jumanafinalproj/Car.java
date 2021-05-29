package com.example.a1171832jumanafinalproj;

public class Car {

    private int year;
    private String manufacturer;
    private  String model;
    private String distance;
    private double price;
    private boolean beenInAccident;
    private boolean beenInOffer;

    public Car() {
    }

    public Car(int year, String manufacturer, String model, String distance, double price, boolean beenInAccident, boolean beenInOffer) {
        this.year = year;
        this.manufacturer = manufacturer;
        this.model = model;
        this.distance = distance;
        this.price = price;
        this.beenInAccident = beenInAccident;
        this.beenInOffer = beenInOffer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBeenInAccident() {
        return beenInAccident;
    }

    public void setBeenInAccident(boolean beenInAccident) {
        this.beenInAccident = beenInAccident;
    }

    public boolean isBeenInOffer() {
        return beenInOffer;
    }

    public void setBeenInOffer(boolean beenInOffer) {
        this.beenInOffer = beenInOffer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "year=" + year +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", distance=" + distance +
                ", price=" + price +
                ", beenInAccident=" + beenInAccident +
                ", beenInOffer=" + beenInOffer +
                '}';
    }
}
