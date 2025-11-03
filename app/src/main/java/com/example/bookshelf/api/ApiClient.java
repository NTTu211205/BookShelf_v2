package com.example.bookshelf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://gutendex.com/";

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)    // Tăng thời gian chờ đọc
                .connectTimeout(30, TimeUnit.SECONDS) // Tăng thời gian chờ kết nối
                .build();
        // --- HẾT PHẦN THÊM ---

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // <-- THÊM CLIENT TÙY CHỈNH VÀO
                .build();
    }
}