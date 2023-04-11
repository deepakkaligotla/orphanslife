package com.kaligotla.oms.AdminView.Child;

import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatus;

import java.io.Serializable;

public class Child implements Serializable {

    private int child_id;
    private String child_name, child_dob, child_gender, admitted_date, leave_date, mother_name, father_name, child_mobile, child_image;
    private AdoptiveStatus adoptiveStatus;
    private Admin admin;
    private String child_created_at, child_updated_at;

    public Child() {
    }

    public Child(int child_id) {
        this.child_id = child_id;
    }

    public Child(String child_name, String child_dob, String child_gender, String admitted_date, String leave_date, String mother_name, String father_name, String child_mobile, String child_image, AdoptiveStatus adoptiveStatus, Admin admin) {
        this.child_name = child_name;
        this.child_dob = child_dob;
        this.child_gender = child_gender;
        this.admitted_date = admitted_date;
        this.leave_date = leave_date;
        this.mother_name = mother_name;
        this.father_name = father_name;
        this.child_mobile = child_mobile;
        this.child_image = child_image;
        this.adoptiveStatus = adoptiveStatus;
        this.admin = admin;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_dob() {
        return child_dob;
    }

    public void setChild_dob(String child_dob) {
        this.child_dob = child_dob;
    }

    public String getChild_gender() {
        return child_gender;
    }

    public void setChild_gender(String child_gender) {
        this.child_gender = child_gender;
    }

    public String getAdmitted_date() {
        return admitted_date;
    }

    public void setAdmitted_date(String admitted_date) {
        this.admitted_date = admitted_date;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getChild_mobile() {
        return child_mobile;
    }

    public void setChild_mobile(String child_mobile) {
        this.child_mobile = child_mobile;
    }

    public String getChild_image() {
        return child_image;
    }

    public void setChild_image(String child_image) {
        this.child_image = child_image;
    }

    public AdoptiveStatus getAdoptiveStatus() {
        return adoptiveStatus;
    }

    public void setAdoptiveStatus(AdoptiveStatus adoptiveStatus) {
        this.adoptiveStatus = adoptiveStatus;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getChild_created_at() {
        return child_created_at;
    }

    public void setChild_created_at(String child_created_at) {
        this.child_created_at = child_created_at;
    }

    public String getChild_updated_at() {
        return child_updated_at;
    }

    public void setChild_updated_at(String child_updated_at) {
        this.child_updated_at = child_updated_at;
    }

    @Override
    public String toString() {
        return "Child{" +
                "child_id=" + child_id +
                ", child_name='" + child_name + '\'' +
                ", child_dob='" + child_dob + '\'' +
                ", child_gender='" + child_gender + '\'' +
                ", admitted_date='" + admitted_date + '\'' +
                ", leave_date='" + leave_date + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", father_name='" + father_name + '\'' +
                ", child_mobile='" + child_mobile + '\'' +
                ", child_image='" + child_image + '\'' +
                ", adoptiveStatus=" + adoptiveStatus +
                ", admin=" + admin +
                ", child_created_at='" + child_created_at + '\'' +
                ", child_updated_at='" + child_updated_at + '\'' +
                '}';
    }
}
