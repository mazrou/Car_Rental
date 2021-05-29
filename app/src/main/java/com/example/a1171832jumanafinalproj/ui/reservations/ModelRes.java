package com.example.a1171832jumanafinalproj.ui.reservations;

public class ModelRes {

    private String carname,distance,accident,offer,resdate,retdate,price,id,carid;

    public ModelRes(String carname, String distance, String accident, String offer, String resdate, String retdate, String price,String id,String carid) {
        this.id=id;
        this.carname = carname;
        this.distance = distance;
        this.accident = accident;
        this.offer = offer;
        this.resdate = resdate;
        this.retdate = retdate;
        this.price = price;
        this.carid=carid;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
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

    public String getResdate() {
        return resdate;
    }

    public void setResdate(String resdate) {
        this.resdate = resdate;
    }

    public String getRetdate() {
        return retdate;
    }

    public void setRetdate(String retdate) {
        this.retdate = retdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
