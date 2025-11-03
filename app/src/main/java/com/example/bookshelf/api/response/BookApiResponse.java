package com.example.bookshelf.api.response;

import com.example.bookshelf.api.models.BookAPI;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookApiResponse {
    @SerializedName("results")
    private List<BookAPI> books;

    @SerializedName("next")
    private String next;

    public BookApiResponse(List<BookAPI> books, String next) {
        this.books = books;
        this.next = next;
    }

    public List<BookAPI> getBooks() {
        return books;
    }

    public void setBooks(List<BookAPI> books) {
        this.books = books;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
