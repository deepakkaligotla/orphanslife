package com.kaligotla.oms.SponsorView;

import com.kaligotla.oms.AdminView.Location.Location;

import java.io.Serializable;
public class Sponsor implements Serializable{

    private int id;

    private String name, dob, gender, govt_id_type, govt_id, mobile, email, password, marital_status,
            user_image, user_address;

    private Location location;

    private String spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile, spouce_image;

    private String created_at;

    private String updated_at;

    public Sponsor() {
    }

    public Sponsor(String name, String email, String password, String mobile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
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

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSpouce_name() {
        return spouce_name;
    }

    public void setSpouce_name(String spouce_name) {
        this.spouce_name = spouce_name;
    }

    public String getSpouce_dob() {
        return spouce_dob;
    }

    public void setSpouce_dob(String spouce_dob) {
        this.spouce_dob = spouce_dob;
    }

    public String getSpouce_govt_id_type() {
        return spouce_govt_id_type;
    }

    public void setSpouce_govt_id_type(String spouce_govt_id_type) {
        this.spouce_govt_id_type = spouce_govt_id_type;
    }

    public String getSpouce_govt_id() {
        return spouce_govt_id;
    }

    public void setSpouce_govt_id(String spouce_govt_id) {
        this.spouce_govt_id = spouce_govt_id;
    }

    public String getSpouce_mobile() {
        return spouce_mobile;
    }

    public void setSpouce_mobile(String spouce_mobile) {
        this.spouce_mobile = spouce_mobile;
    }

    public String getSpouce_image() {
        return spouce_image;
    }

    public void setSpouce_image(String spouce_image) {
        this.spouce_image = spouce_image;
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
        return "Sponsor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", govt_id_type='" + govt_id_type + '\'' +
                ", govt_id='" + govt_id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", user_image='" + user_image + '\'' +
                ", user_address='" + user_address + '\'' +
                ", location=" + location +
                ", spouce_name='" + spouce_name + '\'' +
                ", spouce_dob='" + spouce_dob + '\'' +
                ", spouce_govt_id_type='" + spouce_govt_id_type + '\'' +
                ", spouce_govt_id='" + spouce_govt_id + '\'' +
                ", spouce_mobile='" + spouce_mobile + '\'' +
                ", spouce_image='" + spouce_image + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
