package com.kaligotla.oms.AdminView.Child;

import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.AdoptiveStatus.AdoptiveStatus;

import java.io.Serializable;

public class Child implements Serializable {

    private int id;
    private String name, dob, gender, admitted_date, leave_date, mother_name, father_name, mobile, child_image;
    private AdoptiveStatus adoptiveStatus;
    private Admin admin;
    private String created_at, updated_at;

    public Child() {
    }

    public Child(String name, String dob, String gender, String admitted_date, String leave_date, String mother_name, String father_name, String mobile, String child_image, AdoptiveStatus adoptiveStatus, Admin admin) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.admitted_date = admitted_date;
        this.leave_date = leave_date;
        this.mother_name = mother_name;
        this.father_name = father_name;
        this.mobile = mobile;
        this.child_image = child_image;
        this.adoptiveStatus = adoptiveStatus;
        this.admin = admin;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", admitted_date='" + admitted_date + '\'' +
                ", leave_date='" + leave_date + '\'' +
                ", mother_name='" + mother_name + '\'' +
                ", father_name='" + father_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", child_image='" + child_image + '\'' +
                ", adoptiveStatus=" + adoptiveStatus +
                ", admin=" + admin +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
