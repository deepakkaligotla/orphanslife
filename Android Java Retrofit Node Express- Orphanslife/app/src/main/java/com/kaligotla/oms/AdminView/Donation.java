package com.kaligotla.oms.AdminView;

import com.kaligotla.oms.SponsorView.Sponsor;

public class Donation {

    private int id;

    private double amount;

    private String payment_status;

    private Sponsor sponsor;

    private String created_at, updated_at;

    public Donation() {
    }

    public Donation(double amount, String payment_status, Sponsor sponsor) {
        this.amount = amount;
        this.payment_status = payment_status;
        this.sponsor = sponsor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
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
        return "Donation{" +
                "id=" + id +
                ", amount=" + amount +
                ", payment_status='" + payment_status + '\'' +
                ", sponsor=" + sponsor +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
