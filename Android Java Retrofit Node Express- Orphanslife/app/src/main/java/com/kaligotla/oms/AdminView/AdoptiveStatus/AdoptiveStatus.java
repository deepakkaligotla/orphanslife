package com.kaligotla.oms.AdminView.AdoptiveStatus;

public class AdoptiveStatus {

    private int adoptive_status_id;
    private String status;

    public AdoptiveStatus() {
    }

    public AdoptiveStatus(String status) {
        this.status = status;
    }

    public int getAdoptive_status_id() {
        return adoptive_status_id;
    }

    public void setAdoptive_status_id(int adoptive_status_id) {
        this.adoptive_status_id = adoptive_status_id;
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
