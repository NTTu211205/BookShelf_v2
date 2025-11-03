package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import com.example.bookshelf.R;
import com.example.bookshelf.adapters.CategoriesAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowAllBookCateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private final String[] categories = {"fiction",          // Tiểu thuyết
            "mystery",          // Trinh thám
            "sciencefiction",   // Khoa học viễn tưởng
            "fantasy",          // Giả tưởng
            "romance",          // Lãng mạn
            "horror",           // Kinh dị
            "adventure",        // Phiêu lưu
            "history",          // Lịch sử
            "philosophy",       // Triết học
            "poetry",           // Thơ ca
            "drama",            // Kịch
            "short stories",     // Truyện ngắn
            "biography",        // Tiểu sử
            "children",         // Sách thiếu nhi
            "education",        // Giáo dục
            "psychology",       // Tâm lý học
            "war",              // Chiến tranh
            "humor",            // Hài hước
            "mythology",        // Thần thoại
            "gothic"};
    ImageButton imgButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_show_all_book_cate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imgButton = findViewById(R.id.btn_back);
        imgButton.setOnClickListener(v ->  {
            finish();
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
                Intent intent = new Intent(ShowAllBookCateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            }
            else if (itemId == R.id.nav_bookstore){
                Intent intent = new Intent(ShowAllBookCateActivity.this, BookCategoriesActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else if(itemId == R.id.nav_search){
                Intent intent = new Intent(ShowAllBookCateActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else if(itemId == R.id.nav_library){
                Intent intent = new Intent(ShowAllBookCateActivity.this, LibraryActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else return false;
        });

        recyclerView = findViewById(R.id.rv_allBookCate);
        List<String> stringList = Arrays.asList(categories);
        CategoriesAdapter adapter = new CategoriesAdapter(stringList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
