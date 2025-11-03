package com.example.bookshelf.models;

public class BookCateItem {
    private int imageResId;
    private String type;


    public BookCateItem(String type,int imageResId) {
        this.type = type;
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getType() {
        return type;
    }
}
