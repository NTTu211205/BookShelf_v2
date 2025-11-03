package com.example.bookshelf.models;

public class NearestRead {
    public int imgResources;
    public String author;
    public String type = "Book";
    public String title;

    public NearestRead(int imgResources, String author, String type,String title) {
        this.imgResources = imgResources;
        this.author = author;
        this.type = type;
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public int getImgResources() {
        return imgResources;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }
}
