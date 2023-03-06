package com.kaligotla.oms.AdminView.AdoptiveStatus;

public class AdoptiveStatus {

    private int id;
    private String status;

    public AdoptiveStatus() {
    }

    public AdoptiveStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ""+status;
    }
}
