package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
// Loại bỏ các imports không dùng nữa nếu bạn chỉ dùng WindowCompat.setDecorFitsSystemWindows()
// import androidx.core.graphics.Insets;
// import androidx.core.view.ViewCompat;
// import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.adapters.LibAdapter;
import com.example.bookshelf.models.LibItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // add
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        //add
        if (bottomNavigationView != null) {
            bottomNavigationView.setPadding(0, 0, 0, 0);
        }

        bottomNavigationView.setSelectedItemId(R.id.nav_library);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(LibraryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            }
            else if (itemId == R.id.nav_bookstore){
                Intent intent = new Intent(LibraryActivity.this, BookCategoriesActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else if(itemId == R.id.nav_search){
                Intent intent = new Intent(LibraryActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            else return itemId == R.id.nav_library;
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_library);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<LibItem> bookList = new ArrayList<>();
        bookList.add(new LibItem(R.drawable.ic_home_24, "15%"));
        bookList.add(new LibItem(R.drawable.ic_library_24, "20%"));

        LibAdapter adapter = new LibAdapter(this, bookList);
        recyclerView.setAdapter(adapter);
    }
}
