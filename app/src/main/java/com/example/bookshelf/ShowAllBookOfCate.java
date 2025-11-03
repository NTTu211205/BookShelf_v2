package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.adapters.BookAPiAdapter;
import com.example.bookshelf.api.ApiClient;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;
import com.example.bookshelf.api.response.BookApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllBookOfCate extends AppCompatActivity {
    RecyclerView rcv_Books;
    ProgressBar progressBar;
    ImageButton imgButton;
    TextView categoriesTitle;
    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    BookAPiAdapter adapter;
    private final GridLayoutManager gridLayout = new GridLayoutManager(ShowAllBookOfCate.this, 3);


    private final ApiService api = ApiClient.getClient().create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_show_all_book_of_cate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");

        categoriesTitle = findViewById(R.id.categoriesTitle);
        categoriesTitle.setText(categoryName);

        imgButton = findViewById(R.id.btn_back);
        imgButton.setOnClickListener(v ->  {
            finish();
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Chi tiết sách");
        }
        rcv_Books = findViewById(R.id.rcv_Books);

        loadRecyclerView(categoryName, 1);

        rcv_Books.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = gridLayout.getChildCount();
                int totalItemCount = gridLayout.getItemCount();

                int firstVisibleItemPostion = gridLayout.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPostion >= totalItemCount && firstVisibleItemPostion >= 0) {
                        page ++;
                        loadRecyclerView(categoryName, page);
                    }
                }

            }
        });
    }


    private void loadRecyclerView(String categoryName, int page) {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        Call<BookApiResponse> call = api.getBooksForCategoryName(categoryName.toLowerCase(), page);
        call.enqueue(new Callback<BookApiResponse>() {
            @Override
            public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookApiResponse bookApiResponse = response.body();
                    List<BookAPI> bookAPI = bookApiResponse.getBooks();

                    if (page == 1) {
                        adapter = new BookAPiAdapter(bookAPI, ShowAllBookOfCate.this);
                        rcv_Books.setAdapter(adapter);
                        rcv_Books.setLayoutManager(gridLayout);
                    }
                    else {
                        adapter.addBooks(bookAPI);
                    }

                    if (bookApiResponse.getNext() == null) {
                        isLastPage = true;
                    }

                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Log.d("ShowAllBookOfCate", "Error");
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
}
