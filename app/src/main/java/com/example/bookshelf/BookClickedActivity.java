package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat

import com.bumptech.glide.Glide;
import com.example.bookshelf.api.ApiClient;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookClickedActivity extends AppCompatActivity {
    ImageView book_cover;
    ImageButton btn_back;
    TextView book_title, book_author, book_category, tvPublishedYear, tvPageNumber, tv_book_description;
    Button btn_read_sample, btn_get;
    private static ApiService api = ApiClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.book_clicked);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");
        load(bookId);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // --- THÊM: BUỘC BottomNavigationView LOẠI BỎ PADDING TỰ ĐỘNG ---
        if (bottomNavigationView != null) {
            bottomNavigationView.setPadding(0, 0, 0, 0);
        }

        btn_read_sample = findViewById(R.id.btn_read_sample);
        btn_read_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookClickedActivity.this, PreviewActivity.class);
                intent.putExtra("bookId", bookId);
                startActivity(intent);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_bookstore);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_bookstore) {
                startActivity(new Intent(this, BookCategoriesActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_library) {
                startActivity(new Intent(this, LibraryActivity.class));
                finish();
                return true;
            }
            else return false;
        });
    }

    private void load(String bookId) {

        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        book_category = findViewById(R.id.book_category);
//        tvPublishedYear = findViewById(R.id.tvPublishedYear);
        book_cover = findViewById(R.id.book_cover);
        tvPageNumber = findViewById(R.id.tvPageNumber);
        tv_book_description = findViewById(R.id.tv_book_description);

        Call<BookAPI> call = api.getBook(bookId);
        call.enqueue(new Callback<BookAPI>() {
            @Override
            public void onResponse(Call<BookAPI> call, Response<BookAPI> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookAPI book = response.body();

                    if (book != null) {
                        book_title.setText(book.getTitle());
                        book_author.setText(book.getAuthors());
                        book_category.setText(book.getSubjects());
                        tvPageNumber.setText("Downloaded · " +book.getDownloadCount() + " times");

                        String linkImage = null;
                        if (book.getFormats() != null && book.getFormats().getImageJpeg() != null) {
                            linkImage = book.getFormats().getImageJpeg();
                        }
                        if (linkImage != null) {
                            linkImage = linkImage.replace("http://", "https://");
                        }
                        Glide.with(BookClickedActivity.this)
                                .load(linkImage)
                                .placeholder(R.drawable.icons8_loading_16)
                                .error(R.drawable.non_thumbnail)
                                .into(book_cover);

                        if (book.getSummaries() != null && !book.getSummaries().isEmpty()) {
                            Spanned spannedText = HtmlCompat.fromHtml(book.getSummaries(), HtmlCompat.FROM_HTML_MODE_LEGACY);
                            tv_book_description.setText(spannedText);
                        }
                        else {
                            tv_book_description.setText("No description");
                        }
                    }

                }
                else {
                    Log.d("Show book Info ", "Error");
                }
            }

            @Override
            public void onFailure(Call<BookAPI> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//    }
}
