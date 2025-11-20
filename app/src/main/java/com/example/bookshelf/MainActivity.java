package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.bookshelf.database.models.BookDB;
import com.example.bookshelf.models.ItemContinueReading;
import com.example.bookshelf.utils.InitialData;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.epub.Main;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout clNovels, clNonfiction;

    private RecyclerView recyclerContinue, recyclerDiscover, recyclerBestSeller;
    private NestedScrollView scrollViewHome;
    private BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    private ApiService api;
    private final String PICKS = "nonfiction";
    private final String NOVELS = "novel";

    @Override
    protected void onStart() {
        super.onStart();
    }

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
        InitialData.createInitData(MainActivity.this);


        api = ApiClient.getClient(MainActivity.this).create(ApiService.class);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                BookDao bookDao = AppDatabase.getDatabase(MainActivity.this).bookDao();
//                bookDao.deleteAll();
//            }
//        }).start();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scrollViewHome = findViewById(R.id.scrollView);
        recyclerContinue = findViewById(R.id.recyclerView_continueReading);
        recyclerBestSeller = findViewById(R.id.recyclerView_bestsellers);
        progressBar = findViewById(R.id.progressBar);
        clNovels = findViewById(R.id.clNovels);
        clNonfiction = findViewById(R.id.clNonfiction);

        List<BookAPI> booksNonFiction = InitialData.philosophyBooks;
        if (booksNonFiction != null) {
            loadRecyclerViewBestSeller(booksNonFiction, "philosophy");
        }
        setupContinueReading(recyclerContinue);

        clNovels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAllBookOfCate.class);
                intent.putExtra("categoryName", NOVELS);
                startActivity(intent);
            }
        });

        clNonfiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAllBookOfCate.class);
                intent.putExtra("categoryName", PICKS);
                startActivity(intent);
            }
        });

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
//    private void callApiGetBookByCategoryName() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        Call<BookApiResponse> call = api.getBooksForCategoryName(PICKS, 1);
//        call.enqueue(new Callback<BookApiResponse>() {
//            @Override
//            public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    BookApiResponse apiResponse = response.body();
//                    List<BookAPI> books = apiResponse.getBooks();
//                    if (books.size() > 11) {
//                        books = books.subList(0, 10);
//                    }
//
//                    if (!books.isEmpty()) {
//                        loadRecyclerViewBestSeller(books);
//                    }
//                    else {
//                        Log.d("non fictions: ", "0");
//                    }
//
//                    progressBar.setVisibility(View.GONE);
//
//                }
//                else {
//                    Log.d("Show Picks: ", "Error");
//                    progressBar.setVisibility(View.GONE);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BookApiResponse> call, Throwable t) {
//                t.printStackTrace();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }

    // function load data to recycler view
    private void loadRecyclerViewBestSeller(List<BookAPI> books, String category) {
        Log.d("nonfiction", books.size() + " ");

        BookAPiAdapter adapter = new BookAPiAdapter(books, MainActivity.this, category);
        recyclerBestSeller.setAdapter(adapter);
        recyclerBestSeller.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupContinueReading(RecyclerView recyclerView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BookDao bookDao = AppDatabase.getDatabase(MainActivity.this).bookDao();
                List<BookDB> bookDBS = bookDao.getAll();
                Log.d("Continue reading", bookDBS.size() + " ");
                ContinueAdapter adapter = new ContinueAdapter(MainActivity.this, bookDBS);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    }
                });
            }
        }).start();
    }
}
