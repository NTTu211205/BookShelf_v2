package com.example.bookshelf.models;

public class ItemContinueReading {
    private int imageResId;
    private String title;
    private String author;
    private String percent;

    public ItemContinueReading(int imageResId, String title, String author, String percent) {
        this.imageResId = imageResId;
        this.title = title;
        this.author = author;
        this.percent = percent;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public String getPercent() {
        return percent;
    }
}
