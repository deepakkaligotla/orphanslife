package com.kaligotla.oms.AdminView.Admin;

import com.kaligotla.oms.AdminView.Location.Location;
import com.kaligotla.oms.AdminView.Role.Role;
import com.kaligotla.oms.orphanage.Orphanage;

import java.io.Serializable;

public class Admin implements Serializable {

    private int admin_id;
    private String admin_name, admin_dob, admin_gender, admin_govt_id_type, admin_govt_id, admin_mobile, admin_email, admin_password, address;
    private Location location;
    private Role role;
    private Orphanage orphanage;
    private String image, created_at, updated_at;

    public Admin() {
    }

    public Admin(int admin_id) {
        this.admin_id = admin_id;
    }

    public Admin(String admin_name, String admin_dob, String admin_gender, String admin_mobile, String admin_email, String admin_password) {
        this.admin_name = admin_name;
        this.admin_dob = admin_dob;
        this.admin_gender = admin_gender;
        this.admin_mobile = admin_mobile;
        this.admin_email = admin_email;
        this.admin_password = admin_password;
    }

    public Admin(int admin_id, String admin_name, String admin_dob, String admin_gender, String admin_govt_id_type, String admin_govt_id, String admin_mobile, String admin_email, String admin_password, String address, Location location, Role role, Orphanage orphanage, String image) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.admin_dob = admin_dob;
        this.admin_gender = admin_gender;
        this.admin_govt_id_type = admin_govt_id_type;
        this.admin_govt_id = admin_govt_id;
        this.admin_mobile = admin_mobile;
        this.admin_email = admin_email;
        this.admin_password = admin_password;
        this.address = address;
        this.location = location;
        this.role = role;
        this.orphanage = orphanage;
        this.image = image;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_dob() {
        return admin_dob;
    }

    public void setAdmin_dob(String admin_dob) {
        this.admin_dob = admin_dob;
    }

    public String getAdmin_gender() {
        return admin_gender;
    }

    public void setAdmin_gender(String admin_gender) {
        this.admin_gender = admin_gender;
    }

    public String getAdmin_govt_id_type() {
        return admin_govt_id_type;
    }

    public void setAdmin_govt_id_type(String admin_govt_id_type) {
        this.admin_govt_id_type = admin_govt_id_type;
    }

    public String getAdmin_govt_id() {
        return admin_govt_id;
    }

    public void setAdmin_govt_id(String admin_govt_id) {
        this.admin_govt_id = admin_govt_id;
    }

    public String getAdmin_mobile() {
        return admin_mobile;
    }

    public void setAdmin_mobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
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
                "admin_id=" + admin_id +
                ", admin_name='" + admin_name + '\'' +
                ", admin_dob='" + admin_dob + '\'' +
                ", admin_gender='" + admin_gender + '\'' +
                ", admin_govt_id_type='" + admin_govt_id_type + '\'' +
                ", admin_govt_id='" + admin_govt_id + '\'' +
                ", admin_mobile='" + admin_mobile + '\'' +
                ", admin_email='" + admin_email + '\'' +
                ", admin_password='" + admin_password + '\'' +
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
