package com.example.bookshelf.models;

public class Book {
    private int coverImageResource;
    private String title;
    private String author;

    // Constructor mà BookCategoriesActivity đang gọi
    public Book(int coverImageResource, String title, String author) {
        this.coverImageResource = coverImageResource;
        this.title = title;
        this.author = author;
    }

    // Getters
    public int getCoverImageResource() {
        return coverImageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
