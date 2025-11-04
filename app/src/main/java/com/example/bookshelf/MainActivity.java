package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.adapters.BookAPiAdapter;
import com.example.bookshelf.adapters.ContinueAdapter;
import com.example.bookshelf.api.ApiClient;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;
import com.example.bookshelf.api.response.BookApiResponse;
import com.example.bookshelf.database.AppDatabase;
import com.example.bookshelf.database.dao.BookDao;
import com.example.bookshelf.models.ItemContinueReading;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerContinue, recyclerDiscover, recyclerBestSeller;
    private NestedScrollView scrollViewHome;
    private BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    private final ApiService api = ApiClient.getClient().create(ApiService.class);
    private final String PICKS = "nonfiction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scrollViewHome = findViewById(R.id.scrollView);
        recyclerContinue = findViewById(R.id.recyclerView_continueReading);
        recyclerBestSeller = findViewById(R.id.recyclerView_bestsellers);
        progressBar = findViewById(R.id.progressBar);

        //------------------------------------------------------
        callApiGetBookByCategoryName();




        //-------------------------------------------------------
        setupContinueRecycler(recyclerContinue, getContinueReadingBooks());
//        setupRecycler(recyclerDiscover, getDiscoverBooks());

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // --- THÊM: BUỘC BottomNavigationView LOẠI BỎ PADDING TỰ ĐỘNG ---
        if (bottomNavigationView != null) {
            bottomNavigationView.setPadding(0, 0, 0, 0);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            }
            else if(itemId == R.id.nav_library){
                Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
            }
            else if (itemId == R.id.nav_bookstore){
                Intent intent = new Intent(MainActivity.this, BookCategoriesActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else if(itemId == R.id.nav_search){
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    // function call api get book by category name
    private void callApiGetBookByCategoryName() {
        progressBar.setVisibility(View.VISIBLE);

        Call<BookApiResponse> call = api.getBooksForCategoryName(PICKS, 1);
        call.enqueue(new Callback<BookApiResponse>() {
            @Override
            public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookApiResponse apiResponse = response.body();
                    List<BookAPI> books = apiResponse.getBooks();
                    if (books.size() > 11) {
                        books = books.subList(0, 10);
                    }

                    if (!books.isEmpty()) {
                        loadRecyclerViewBestSeller(books);
                    }
                    else {
                        Log.d("non fictions: ", "0");
                    }

                    progressBar.setVisibility(View.GONE);

                }
                else {
                    Log.d("Show Picks: ", "Error");
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<BookApiResponse> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // function load data to recycler view
    private void loadRecyclerViewBestSeller(List<BookAPI> books) {
        Log.d("nonfiction", books.size() + " ");

        BookAPiAdapter adapter = new BookAPiAdapter(books, MainActivity.this);
        recyclerBestSeller.setAdapter(adapter);
        recyclerBestSeller.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupContinueRecycler(RecyclerView recyclerView, List<ItemContinueReading> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new ContinueAdapter(this, items));
    }

    private List<ItemContinueReading> getContinueReadingBooks() {
        List<ItemContinueReading> list = new ArrayList<>();
        list.add(new ItemContinueReading(R.drawable.ic_arrow_forward, "Lịch sử phần mềm", "Tác giả Trần B", "15"));
        list.add(new ItemContinueReading(R.drawable.ic_home_24, "Lập trình Web", "Tác giả A", "20"));
        list.add(new ItemContinueReading(R.drawable.ic_library_24, "Tâm lý học", "Tác giả X", "55"));
        return list;
    }
}
