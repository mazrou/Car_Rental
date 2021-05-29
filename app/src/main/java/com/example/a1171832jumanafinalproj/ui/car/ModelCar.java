package com.example.a1171832jumanafinalproj.ui.car;

public class ModelCar {

    private int image;
    private String name,model,price,manufacturer,distance,accident,offer,year,status;


    public ModelCar(int image, String name, String model, String price, String manufacturer, String distance, String accident, String offer, String year,String status) {
        this.image = image;
        this.name = name;
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.distance = distance;
        this.accident = accident;
        this.offer = offer;
        this.year = year;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}


