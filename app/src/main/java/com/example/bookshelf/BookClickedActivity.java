package com.example.bookshelf;

import android.content.Context;
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
import com.example.bookshelf.database.AppDatabase;
import com.example.bookshelf.database.dao.BookDao;
import com.example.bookshelf.database.models.BookDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.internal.http2.Http2;
import okhttp3.internal.http2.Http2Connection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookClickedActivity extends AppCompatActivity {
    ImageView book_cover;
    ImageButton btn_back;
    TextView book_title, book_author, book_category, tvPublishedYear, tvPageNumber, tv_book_description;
    Button btn_read_sample, btn_get;
    private BookAPI bookAPI;
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

        btn_get = findViewById(R.id.btn_get);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BookDao bookDao = AppDatabase.getDatabase(BookClickedActivity.this).bookDao();
                        BookDB book = bookDao.getBook(bookAPI.getId());
                        if (book != null) {
                            Log.d("Notification", "Bạn đã lưu sách này rồi");
                            return;
                        }
                        String fileName = String.valueOf(bookAPI.getId() + ".epub");
                        String thumbnaiName = fileName + "_image.jpg" ;

                        BookDB bookDB = new BookDB(
                                bookAPI.getId(),
                                bookAPI.getTitle(),
                                bookAPI.getAuthors(),
                                bookAPI.getSummaries(),
                                bookAPI.getEditors(),
                                bookAPI.getTranslators(),
                                bookAPI.getSubjects(),
                                bookAPI.getBookshelves(),
                                bookAPI.getLanguages(),
                                bookAPI.getCopyright(),
                                bookAPI.getMediaType(),
                                bookAPI.getDownloadCount(),
                                fileName,
                                thumbnaiName
                        );

                        insertBook(bookDB);
                        download(BookClickedActivity.this, bookAPI.getFormats().getApplicationEpubZip(), fileName);
                        download(BookClickedActivity.this, bookAPI.getFormats().getImageJpeg(), thumbnaiName);
                        List<BookDB> books = bookDao.getAll();

                        for (BookDB b:books) {
                            Log.d("db", b.getId() + ", " + b.getTitle());
                        }
                    }
                }).start();
            }
        });
    }


    private void download(Context context, String linkDownload, String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // noi luu file trong app
                    File file = new File (context.getFilesDir(), fileName);

                    if (file.exists()) {
                        Log.d("Download", "Ban da tai sach nay");
                        return;
                    }

                    // ket noi voi duong dan da cung cap
                    URL url = new URL(linkDownload);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.connect();

                    // mo file va thuc hien doc file
                    InputStream input = connect.getInputStream();
                    FileOutputStream output = new FileOutputStream(file);

                    byte[] buffer = new byte[4096];
                    int len;

                    while ((len = input.read(buffer)) != -1) {
                        output.write(buffer, 0, len);
                    }

                    output.flush();
                    output.close();
                    input.close();

                    Log.d("Download", "Tai thanh cong " + fileName);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Download", "Tai KHONG thanh cong " + fileName);

                }
            }
        }).start();
    }

    private void insertBook(BookDB book) {
        BookDao bookDao = AppDatabase.getDatabase(BookClickedActivity.this).bookDao();
        bookDao.insert(book);
    }

    private void load(String bookId) {
        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.book_author);
        book_category = findViewById(R.id.book_category);
        book_cover = findViewById(R.id.book_cover);
        tvPageNumber = findViewById(R.id.tvPageNumber);
        tv_book_description = findViewById(R.id.tv_book_description);

        Call<BookAPI> call = api.getBook(bookId);
        call.enqueue(new Callback<BookAPI>() {
            @Override
            public void onResponse(Call<BookAPI> call, Response<BookAPI> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookAPI = response.body();

                    if (bookAPI != null) {
                        book_title.setText(bookAPI.getTitle());
                        book_author.setText(bookAPI.getAuthors());
                        book_category.setText(bookAPI.getSubjects());
                        tvPageNumber.setText("Downloaded · " + bookAPI.getDownloadCount() + " times");

                        String linkImage = null;
                        if (bookAPI.getFormats() != null && bookAPI.getFormats().getImageJpeg() != null) {
                            linkImage = bookAPI.getFormats().getImageJpeg();
                        }
                        if (linkImage != null) {
                            linkImage = linkImage.replace("http://", "https://");
                        }
                        Glide.with(BookClickedActivity.this)
                                .load(linkImage)
                                .placeholder(R.drawable.icons8_loading_16)
                                .error(R.drawable.non_thumbnail)
                                .into(book_cover);

                        if (bookAPI.getSummaries() != null && !bookAPI.getSummaries().isEmpty()) {
                            Spanned spannedText = HtmlCompat.fromHtml(bookAPI.getSummaries(), HtmlCompat.FROM_HTML_MODE_LEGACY);
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
}
