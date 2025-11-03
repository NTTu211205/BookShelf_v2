package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View; // Thêm import
import android.widget.Button;
import android.widget.TextView; // Thêm import

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.adapters.BookAPiAdapter;
import com.example.bookshelf.adapters.BookCateAdapter; // Import adapter
import com.example.bookshelf.api.ApiClient;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;
import com.example.bookshelf.api.response.BookApiResponse;
import com.example.bookshelf.models.BookCateItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class BookCategoriesActivity extends AppCompatActivity implements BookCateAdapter.OnCategoryClickListener {
public class BookCategoriesActivity extends AppCompatActivity {

    private final String NOVELS = "novels";
    private final String CHILD_AND_TEENAGER = "child";
    private final String NON_FICTION = "nonfiction";
    private final String SHORT_STORIES = "short stories";
    private final String POEMS = "poems";

    private final ApiService api = ApiClient.getClient().create(ApiService.class);

    private RecyclerView recyclerFeatured;
    private RecyclerView recyclerNovel, recyclerChild, recyclerNonFic, recyclerShortStories, recyclerPoems;
    private TextView tvNovel, tvChild, tvNonFic, tvShortStories, tvPoems;
    Button btShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.book_cate_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // --- THÊM: BUỘC BottomNavigationView LOẠI BỎ PADDING TỰ ĐỘNG ---
        if (bottomNavigationView != null) {
            bottomNavigationView.setPadding(0, 0, 0, 0);
        }

        bottomNavigationView.setSelectedItemId(R.id.nav_bookstore);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(BookCategoriesActivity.this, MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_library) {
                startActivity(new Intent(BookCategoriesActivity.this, LibraryActivity.class));
                finish();
                return true;
            } else if(itemId == R.id.nav_search) {
                startActivity(new Intent(BookCategoriesActivity.this, SearchActivity.class));
                finish();
                return true;
            }
            return itemId == R.id.nav_bookstore;
        });

        btShowAll = findViewById(R.id.btShowAll);
        btShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookCategoriesActivity.this, ShowAllBookCateActivity.class);
                startActivity(intent);
            }
        });

        tvNovel = findViewById(R.id.tvNovelAndLiterature);
        tvChild = findViewById(R.id.tvChild);
        tvNonFic = findViewById(R.id.tv_nonFic);
        tvShortStories = findViewById(R.id.tv_ShortStory);
        tvPoems = findViewById(R.id.tv_poems);

        recyclerFeatured = findViewById(R.id.recyclerView_featured);
        recyclerFeatured.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        BookCateAdapter featuredAdapter = new BookCateAdapter(this, getFeaturedItems());
        recyclerFeatured.setAdapter(featuredAdapter);

        recyclerNovel = findViewById(R.id.rcNovelAndLiterature);
        recyclerChild = findViewById(R.id.recyclerView_child);
        recyclerNonFic = findViewById(R.id.recycler_nonFic);
        recyclerShortStories = findViewById(R.id.recycler_shortStories);
        recyclerPoems = findViewById(R.id.recycler_poems);

        callApiGetBooksByCategory(NOVELS, recyclerNovel);
        callApiGetBooksByCategory(CHILD_AND_TEENAGER, recyclerChild);
        callApiGetBooksByCategory(NON_FICTION, recyclerNonFic);
        callApiGetBooksByCategory(SHORT_STORIES, recyclerShortStories);
        callApiGetBooksByCategory(POEMS, recyclerPoems);
    }

    private List<BookCateItem> getFeaturedItems() {
        List<BookCateItem> list = new ArrayList<>();
        list.add(new BookCateItem("Non fiction",R.drawable.non_fiction_poster));
        list.add(new BookCateItem("History",R.drawable.history_poster));
        list.add(new BookCateItem("Business",R.drawable.poster_business));
        list.add(new BookCateItem("Computer and Programming",R.drawable.poster_computer));
        list.add(new BookCateItem("Science",R.drawable.poster_science));
        return list;
    }

    private void callApiGetBooksByCategory(String category, RecyclerView recyclerView) {
        Call<BookApiResponse> call = api.getBooksForCategoryName(category);
        call.enqueue(new Callback<BookApiResponse>() {
            @Override
            public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookAPI> books = response.body().getBooks();
                    if (!books.isEmpty()) {
                        loadRecyclerView(recyclerView, books);
                        Log.d(category, books.size() + " ");

                    }
                } else {
                    Log.d("Show novels", "Error");
                }
            }

            @Override
            public void onFailure(Call<BookApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadRecyclerView(RecyclerView recyclerView, List<BookAPI> books) {
        recyclerView.setLayoutManager(new LinearLayoutManager(BookCategoriesActivity.this, LinearLayoutManager.HORIZONTAL, false));
        BookAPiAdapter adapter = new BookAPiAdapter(books, BookCategoriesActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
