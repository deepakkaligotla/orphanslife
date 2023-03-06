package com.kaligotla.oms.AdminView.Admin;

import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.orphanage.Orphanage;

import java.io.Serializable;

public class Admin implements Serializable {

    private int id;
    private String name, dob, gender, govt_id_type, govt_id, mobile, email, password, address;
    private Location location;
    private Role role;
    private Orphanage orphanage;
    private String image, created_at, updated_at;

    public Admin() {
    }

    public Admin(String name, String dob, String gender, String govt_id_type, String govt_id, String mobile, String email, String password, String address, Location location, Role role, Orphanage orphanage, String image) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.govt_id_type = govt_id_type;
        this.govt_id = govt_id;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.address = address;
        this.location = location;
        this.role = role;
        this.orphanage = orphanage;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGovt_id_type() {
        return govt_id_type;
    }

    public void setGovt_id_type(String govt_id_type) {
        this.govt_id_type = govt_id_type;
    }

    public String getGovt_id() {
        return govt_id;
    }

    public void setGovt_id(String govt_id) {
        this.govt_id = govt_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Orphanage getOrphanage() {
        return orphanage;
    }

    public void setOrphanage(Orphanage orphanage) {
        this.orphanage = orphanage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", govt_id_type='" + govt_id_type + '\'' +
                ", govt_id='" + govt_id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", role=" + role +
                ", orphanage=" + orphanage +
                ", image='" + image + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
