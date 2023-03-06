package com.kaligotla.oms.orphanage;


import com.kaligotla.oms.AdminView.Location.Location;

import java.io.Serializable;

public class Orphanage implements Serializable {
    private int id;
    private String type, address;
    private Location location;
    private String contact_person, mobile, phone, email;
    private int in_home, adoptable, boys, girls;
    private String orphanage_image, created_at, updated_at;

    public Orphanage() {
    }

    public Orphanage(int id) {
        this.id = id;
    }

    public Orphanage(String address) {
        this.address = address;
    }

    public Orphanage(String type, String address, Location location, String contact_person, String mobile, String phone, String email, int in_home, int adoptable, int boys, int girls, String orphanage_image) {
        this.type = type;
        this.address = address;
        this.location = location;
        this.contact_person = contact_person;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
        this.in_home = in_home;
        this.adoptable = adoptable;
        this.boys = boys;
        this.girls = girls;
        this.orphanage_image = orphanage_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIn_home() {
        return in_home;
    }

    public void setIn_home(int in_home) {
        this.in_home = in_home;
    }

    public int getAdoptable() {
        return adoptable;
    }

    public void setAdoptable(int adoptable) {
        this.adoptable = adoptable;
    }

    public int getBoys() {
        return boys;
    }

    public void setBoys(int boys) {
        this.boys = boys;
    }

    public int getGirls() {
        return girls;
    }

    public void setGirls(int girls) {
        this.girls = girls;
    }

    public String getOrphanage_image() {
        return orphanage_image;
    }

    public void setOrphanage_image(String orphanage_image) {
        this.orphanage_image = orphanage_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Orphanage{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", contact_person='" + contact_person + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", in_home=" + in_home +
                ", adoptable=" + adoptable +
                ", boys=" + boys +
                ", girls=" + girls +
                ", orphanage_image='" + orphanage_image + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
