package com.example.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager; // <-- THÊM IMPORT
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Giữ nguyên import này
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.adapters.BookAPiAdapter;
import com.example.bookshelf.adapters.SearchTermAdapter;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;
import com.example.bookshelf.api.response.BookApiResponse;
import com.example.bookshelf.database.AppDatabase;
import com.example.bookshelf.database.dao.SearchDao;
import com.example.bookshelf.database.models.SearchHistory;
import com.example.bookshelf.api.ApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private final ApiService api = ApiClient.getClient().create(ApiService.class);
    LinearLayout lnGone, lnNearestSearch;
    RecyclerView rvBooks, rcNearest;
    SearchView searchView;
    SearchTermAdapter adapter;
    List<SearchHistory> searchHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.search_page);

        // Khắc phục lỗi thanh trạng thái đè (Đã sửa ở lần trước)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Buộc BottomNavigationView lấp đầy khoảng trống dưới (Đã sửa ở lần trước)
        if (bottomNavigationView != null) {
            bottomNavigationView.setPadding(0, 0, 0, 0);
        }

        bottomNavigationView.setSelectedItemId(R.id.nav_search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            } else if (itemId == R.id.nav_bookstore) {
                Intent intent = new Intent(SearchActivity.this, BookCategoriesActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (itemId == R.id.nav_library) {
                Intent intent = new Intent(SearchActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            return itemId == R.id.nav_search;
        });

        lnGone = findViewById(R.id.lnGone);
        lnNearestSearch = findViewById(R.id.lnNearestSearch);
        rvBooks = findViewById(R.id.rvBooks);
        rcNearest = findViewById(R.id.rcNearest);
        searchView = findViewById(R.id.search_input_view);
        loadRecyclerViewNearestSearch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    lnGone.setVisibility(View.GONE);
                    lnNearestSearch.setVisibility(View.VISIBLE);
                    loadRecyclerViewNearestSearch();
                }
                else{
                    lnGone.setVisibility(View.VISIBLE);
                    lnNearestSearch.setVisibility(View.GONE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                // 1. GỌI HÀM ẨN BÀN PHÍM
                hideKeyboard();

                // 2. XÓA TIÊU ĐIỂM KHỎI SEARCHVIEW
                searchView.clearFocus();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertDB(query);
                    }
                }).start();

                Call<BookApiResponse> call = api.getBooksForCategoryName(query);
                call.enqueue(new Callback<BookApiResponse>() {
                    @Override
                    public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            BookApiResponse bookApiResponse = response.body();
                            List<BookAPI> bookAPIS = bookApiResponse.getBooks();

                            BookAPiAdapter bookAPiAdapter = new BookAPiAdapter(bookAPIS, SearchActivity.this);
                            rvBooks.setLayoutManager(new GridLayoutManager(SearchActivity.this, 3));
                            rvBooks.setAdapter(bookAPiAdapter);
                        }
                        else {
                            Log.d("Search Activity", "Error call api search");
                        }
                    }

                    @Override
                    public void onFailure(Call<BookApiResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                return true;
            }
        });
    }

    private void insertDB(String content) {
        Log.d("Search History", "Added to db");
        SearchDao searchDao = AppDatabase.getDatabase(SearchActivity.this).searchDao();
        searchDao.insert(new SearchHistory(content));
    }

    private void loadRecyclerViewNearestSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SearchDao searchDao = AppDatabase.getDatabase(SearchActivity.this).searchDao();
                searchHistories = searchDao.getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Search History List", searchHistories.size() + "");
                        adapter = new SearchTermAdapter(SearchActivity.this, searchHistories);
                        Log.d("Adapter", adapter.toString());
                        rcNearest.setAdapter(adapter);
                        rcNearest.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    }
                });
            }
        }).start();
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
