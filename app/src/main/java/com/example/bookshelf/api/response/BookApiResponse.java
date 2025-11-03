package com.example.bookshelf.api.response;

import com.example.bookshelf.api.models.BookAPI;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookApiResponse {
    @SerializedName("results")
    private List<BookAPI> books;

    public BookApiResponse(List<BookAPI> books) {
        this.books = books;
    }

    public List<BookAPI> getBooks() {
        return books;
    }

    public void setBooks(List<BookAPI> books) {
        this.books = books;
    }
}
