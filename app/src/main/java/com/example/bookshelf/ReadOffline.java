package com.example.bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

public class ReadOffline extends AppCompatActivity {
    ImageButton btNext, btPre, btn_back;
    WebView wvRead;
    TextView tvTitle;
    List<Resource> chapters;
    int nowChapter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_offline);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wvRead = findViewById(R.id.wvRead);
        btPre = findViewById(R.id.btPre);
        btNext = findViewById(R.id.btNext);
        tvTitle = findViewById(R.id.tvTitle);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        String title = intent.getStringExtra("title");
        openEpub(fileName);

        tvTitle.setText(title);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowChapter == chapters.size() - 1) {
                    return;
                }

                nowChapter++;
                displayChapter(nowChapter);
            }
        });

        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowChapter == 1) {
                    return;
                }

                nowChapter--;
                displayChapter(nowChapter);
            }
        });
    }

    private void openEpub(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            InputStream epubInputStream = new FileInputStream(file);

            Book book = (new EpubReader()).readEpub(epubInputStream);

            chapters = book.getContents();

            displayChapter(nowChapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayChapter(int index) {
        if (chapters == null || chapters.isEmpty()) {
            return;
        }

        try {
            Log.d("Load Book", "Load success");
            Resource chapter = chapters.get(index);
            String css = "<style>body { background-color:#121212; color:#E0E0E0; font-size:110%; line-height:1.6; } a { color:#81D4FA; }</style>";
            String epubContent = new String (chapter.getData(), StandardCharsets.UTF_8);
            String html = epubContent + css;
            wvRead.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}