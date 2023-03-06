package com.kaligotla.oms.AdminView.AdoptRequest;

import com.kaligotla.oms.AdminView.Admin.Admin;
import com.kaligotla.oms.AdminView.Child.Child;
import com.kaligotla.oms.SponsorView.Sponsor;

import java.io.Serializable;

public class AdoptRequest implements Serializable {

    private int req_no;

    private Sponsor sponsor;

    private Admin admin;

    private Child child;

    private String reason, req_stage, date_of_req, last_checked, req_comment, next_check, adopted;

    public AdoptRequest() {
    }

    public AdoptRequest(Sponsor sponsor, Admin admin, Child child, String reason) {
        this.sponsor = sponsor;
        this.admin = admin;
        this.child = child;
        this.reason = reason;
    }

    public int getReq_no() {
        return req_no;
    }

    public void setReq_no(int req_no) {
        this.req_no = req_no;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReq_stage() {
        return req_stage;
    }

    public void setReq_stage(String req_stage) {
        this.req_stage = req_stage;
    }

    public String getDate_of_req() {
        return date_of_req;
    }

    public void setDate_of_req(String date_of_req) {
        this.date_of_req = date_of_req;
    }

    public String getLast_checked() {
        return last_checked;
    }

    public void setLast_checked(String last_checked) {
        this.last_checked = last_checked;
    }

    public String getReq_comment() {
        return req_comment;
    }

    public void setReq_comment(String req_comment) {
        this.req_comment = req_comment;
    }

    public String getNext_check() {
        return next_check;
    }

    public void setNext_check(String next_check) {
        this.next_check = next_check;
    }

    public String getAdopted() {
        return adopted;
    }

    public void setAdopted(String adopted) {
        this.adopted = adopted;
    }

    @Override
    public String toString() {
        return "AdoptRequest{" +
                "req_no=" + req_no +
                ", sponsor=" + sponsor +
                ", admin=" + admin +
                ", child=" + child +
                ", reason='" + reason + '\'' +
                ", req_stage='" + req_stage + '\'' +
                ", date_of_req='" + date_of_req + '\'' +
                ", last_checked='" + last_checked + '\'' +
                ", req_comment='" + req_comment + '\'' +
                ", next_check='" + next_check + '\'' +
                ", adopted='" + adopted + '\'' +
                '}';
    }
}
