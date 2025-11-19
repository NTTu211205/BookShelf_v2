package com.example.bookshelf.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://gutendex.com/";

//    private static boolean hasNetwork(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
//            return activeNetwork != null && activeNetwork.isConnected();
//        }
//        return false;
//    }

    public static Retrofit getClient(Context context) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .create();

//        Cache cache = new Cache(context.getCacheDir(), 100 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)    // Tăng thời gian chờ đọc
                .connectTimeout(60, TimeUnit.SECONDS) // Tăng thời gian chờ kết nối
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // THÊM CLIENT TÙY CHỈNH VÀO
                .build();

    }
}