package com.kaligotla.oms.AdminView.Location;

public class Pagination {

    private int page, size;

    public Pagination() {
    }
    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
