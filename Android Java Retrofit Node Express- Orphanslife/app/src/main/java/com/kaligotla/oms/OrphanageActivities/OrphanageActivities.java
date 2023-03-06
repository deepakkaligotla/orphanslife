package com.kaligotla.oms.OrphanageActivities;

import com.kaligotla.oms.orphanage.Orphanage;

public class OrphanageActivities {
    private int event_id;

    private Orphanage orphanage;

    private String details, image_1, image_2, image_3, image_4, image_5;

    public OrphanageActivities() {
    }

    public OrphanageActivities(Orphanage orphanage, String details, String image_1, String image_2, String image_3, String image_4, String image_5) {
        this.orphanage = orphanage;
        this.details = details;
        this.image_1 = image_1;
        this.image_2 = image_2;
        this.image_3 = image_3;
        this.image_4 = image_4;
        this.image_5 = image_5;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public Orphanage getOrphanage() {
        return orphanage;
    }

    public void setOrphanage(Orphanage orphanage) {
        this.orphanage = orphanage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getImage_3() {
        return image_3;
    }

    public void setImage_3(String image_3) {
        this.image_3 = image_3;
    }

    public String getImage_4() {
        return image_4;
    }

    public void setImage_4(String image_4) {
        this.image_4 = image_4;
    }

    public String getImage_5() {
        return image_5;
    }

    public void setImage_5(String image_5) {
        this.image_5 = image_5;
    }

    @Override
    public String toString() {
        return "OrphanageActivities{" +
                "orphanage_id=" + orphanage.getId() +
                ", details='" + details + '\'' +
                ", image_1='" + image_1 + '\'' +
                ", image_2='" + image_2 + '\'' +
                ", image_3='" + image_3 + '\'' +
                ", image_4='" + image_4 + '\'' +
                ", image_5='" + image_5 + '\'' +
                '}';
    }
}
