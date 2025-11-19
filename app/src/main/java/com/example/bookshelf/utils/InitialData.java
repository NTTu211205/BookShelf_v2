package com.example.bookshelf.utils;

import android.content.Context;

import com.example.bookshelf.api.models.BookAPI;
import com.example.bookshelf.api.response.BookApiResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InitialData {
    private static final String[] fileNames = {
            "sample_data_childandteenager.json",
            "sample_data_nonfiction.json",
            "sample_data_novels.json",
            "sample_data_poems.json",
            "sample_data_shortstories.json",
            "sample_data_philosophy.json"
    };

    public static List<BookAPI> nonFictionBooks;
    public static List<BookAPI> shortStoriesBooks;
    public static List<BookAPI> poemsBooks;
    public static List<BookAPI> childAndTeenagerBooks;
    public static List<BookAPI> novelsBooks;
    public static List<BookAPI> philosophyBooks;

    public static void createInitData(Context context) {
        childAndTeenagerBooks = loadBooksFromAssets(context, fileNames[0]);
        nonFictionBooks = loadBooksFromAssets(context, fileNames[1]);
        novelsBooks = loadBooksFromAssets(context, fileNames[2]);
        poemsBooks = loadBooksFromAssets(context, fileNames[3]);
        shortStoriesBooks = loadBooksFromAssets(context, fileNames[4]);
        philosophyBooks = loadBooksFromAssets(context, fileNames[5]);
    }

    private static List<BookAPI> loadBooksFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            // 1. Mở file từ assets
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // 2. Chuyển thành String
            jsonString = new String(buffer, StandardCharsets.UTF_8);

            // 3. Parse JSON bằng Gson
            Gson gson = new Gson();
            // Giả sử API trả về object tổng có chứa field "results" là list sách
            BookApiResponse response = gson.fromJson(jsonString, BookApiResponse.class);

            return response.getBooks(); // Trả về danh sách sách

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
