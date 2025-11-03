package com.example.bookshelf.models;

public class LibItem {
    private int imageResId;
    private String percent;

    public LibItem(int imageResId, String percent) {
        this.imageResId = imageResId;
        this.percent = percent;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPercent(){
        return percent;
    }
}
