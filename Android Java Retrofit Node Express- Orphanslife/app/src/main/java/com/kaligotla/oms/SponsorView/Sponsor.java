package com.kaligotla.oms.SponsorView;

import com.kaligotla.oms.AdminView.Location.Location;

import java.io.Serializable;
public class Sponsor implements Serializable{

    private int sponsor_id;

    private String sponsor_name, sponsor_dob, sponsor_gender, sponsor_govt_id_type, sponsor_govt_id, sponsor_mobile, sponsor_email, sponsor_password, marital_status,
            sponsor_image, sponsor_address;

    private Location location;

    private String spouce_name, spouce_dob, spouce_govt_id_type, spouce_govt_id, spouce_mobile, spouce_image;

    private String sponsor_created_at;

    private String sponsor_updated_at;

    public Sponsor() {
    }

    public Sponsor(int sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public Sponsor(String sponsor_name, String sponsor_dob, String sponsor_gender, String sponsor_mobile, String sponsor_email, String sponsor_password) {
        this.sponsor_name = sponsor_name;
        this.sponsor_dob = sponsor_dob;
        this.sponsor_gender = sponsor_gender;
        this.sponsor_mobile = sponsor_mobile;
        this.sponsor_email = sponsor_email;
        this.sponsor_password = sponsor_password;
    }

    public Sponsor(int sponsor_id, String sponsor_name, String sponsor_dob, String sponsor_gender, String sponsor_govt_id_type, String sponsor_govt_id, String sponsor_mobile, String sponsor_email, String sponsor_password, String marital_status, String sponsor_image, String sponsor_address) {
        this.sponsor_id = sponsor_id;
        this.sponsor_name = sponsor_name;
        this.sponsor_dob = sponsor_dob;
        this.sponsor_gender = sponsor_gender;
        this.sponsor_govt_id_type = sponsor_govt_id_type;
        this.sponsor_govt_id = sponsor_govt_id;
        this.sponsor_mobile = sponsor_mobile;
        this.sponsor_email = sponsor_email;
        this.sponsor_password = sponsor_password;
        this.marital_status = marital_status;
        this.sponsor_image = sponsor_image;
        this.sponsor_address = sponsor_address;
    }

    public Sponsor(Sponsor s) {
        this.sponsor_id = s.sponsor_id;
        this.sponsor_name = s.sponsor_name;
        this.sponsor_dob = s.sponsor_dob;
        this.sponsor_gender = s.sponsor_gender;
        this.sponsor_govt_id_type = s.sponsor_govt_id_type;
        this.sponsor_govt_id = s.sponsor_govt_id;
        this.sponsor_mobile = s.sponsor_mobile;
        this.sponsor_email = s.sponsor_email;
        this.sponsor_password = s.sponsor_password;
        this.marital_status = s.marital_status;
        this.sponsor_image = s.sponsor_image;
        this.sponsor_address = s.sponsor_address;
        this.location = s.location;
        this.spouce_name = s.spouce_name;
        this.spouce_dob = s.spouce_dob;
        this.spouce_govt_id_type = s.spouce_govt_id_type;
        this.spouce_govt_id = s.spouce_govt_id;
        this.spouce_mobile = s.spouce_mobile;
        this.spouce_image = s.spouce_image;
        this.sponsor_created_at = s.sponsor_created_at;
        this.sponsor_updated_at = s.sponsor_updated_at;
    }

    public int getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(int sponsor_id) {
        this.sponsor_id = sponsor_id;
    }

    public String getSponsor_name() {
        return sponsor_name;
    }

    public void setSponsor_name(String sponsor_name) {
        this.sponsor_name = sponsor_name;
    }

    public String getSponsor_dob() {
        return sponsor_dob;
    }

    public void setSponsor_dob(String sponsor_dob) {
        this.sponsor_dob = sponsor_dob;
    }

    public String getSponsor_gender() {
        return sponsor_gender;
    }

    public void setSponsor_gender(String sponsor_gender) {
        this.sponsor_gender = sponsor_gender;
    }

    public String getSponsor_govt_id_type() {
        return sponsor_govt_id_type;
    }

    public void setSponsor_govt_id_type(String sponsor_govt_id_type) {
        this.sponsor_govt_id_type = sponsor_govt_id_type;
    }

    public String getSponsor_govt_id() {
        return sponsor_govt_id;
    }

    public void setSponsor_govt_id(String sponsor_govt_id) {
        this.sponsor_govt_id = sponsor_govt_id;
    }

    public String getSponsor_mobile() {
        return sponsor_mobile;
    }

    public void setSponsor_mobile(String sponsor_mobile) {
        this.sponsor_mobile = sponsor_mobile;
    }

    public String getSponsor_email() {
        return sponsor_email;
    }

    public void setSponsor_email(String sponsor_email) {
        this.sponsor_email = sponsor_email;
    }

    public String getSponsor_password() {
        return sponsor_password;
    }

    public void setSponsor_password(String sponsor_password) {
        this.sponsor_password = sponsor_password;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getSponsor_image() {
        return sponsor_image;
    }

    public void setSponsor_image(String sponsor_image) {
        this.sponsor_image = sponsor_image;
    }

    public String getSponsor_address() {
        return sponsor_address;
    }

    public void setSponsor_address(String sponsor_address) {
        this.sponsor_address = sponsor_address;
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

    public String getSponsor_created_at() {
        return sponsor_created_at;
    }

    public void setSponsor_created_at(String sponsor_created_at) {
        this.sponsor_created_at = sponsor_created_at;
    }

    public String getSponsor_updated_at() {
        return sponsor_updated_at;
    }

    public void setSponsor_updated_at(String sponsor_updated_at) {
        this.sponsor_updated_at = sponsor_updated_at;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "sponsor_id=" + sponsor_id +
                ", sponsor_name='" + sponsor_name + '\'' +
                ", sponsor_dob='" + sponsor_dob + '\'' +
                ", sponsor_gender='" + sponsor_gender + '\'' +
                ", sponsor_govt_id_type='" + sponsor_govt_id_type + '\'' +
                ", sponsor_govt_id='" + sponsor_govt_id + '\'' +
                ", sponsor_mobile='" + sponsor_mobile + '\'' +
                ", sponsor_email='" + sponsor_email + '\'' +
                ", sponsor_password='" + sponsor_password + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", sponsor_image='" + sponsor_image + '\'' +
                ", sponsor_address='" + sponsor_address + '\'' +
                ", location=" + location +
                ", spouce_name='" + spouce_name + '\'' +
                ", spouce_dob='" + spouce_dob + '\'' +
                ", spouce_govt_id_type='" + spouce_govt_id_type + '\'' +
                ", spouce_govt_id='" + spouce_govt_id + '\'' +
                ", spouce_mobile='" + spouce_mobile + '\'' +
                ", spouce_image='" + spouce_image + '\'' +
                ", sponsor_created_at='" + sponsor_created_at + '\'' +
                ", sponsor_updated_at='" + sponsor_updated_at + '\'' +
                '}';
    }
}
