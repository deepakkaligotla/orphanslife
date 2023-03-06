package com.kaligotla.oms.AdminView.Location;

import java.io.Serializable;

public class Location implements Serializable {
    private int id, pincode;
    private String area, city, district, state;

    public Location() {
    }

    public Location(int pincode, String area, String city, String district, String state) {
        this.pincode = pincode;
        this.area = area;
        this.city = city;
        this.district = district;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return ""+pincode+"\n"+area+"\n"+city+"\n"+district+"\n"+state;
    }
}
