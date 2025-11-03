package com.example.bookshelf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat; // Thêm import WindowCompat

import com.example.bookshelf.api.ApiClient;
import com.example.bookshelf.api.ApiService;
import com.example.bookshelf.api.models.BookAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// (Loại bỏ import BottomNavigationView vì không dùng)

public class PreviewActivity extends AppCompatActivity {
    WebView wbPreview;
    ImageButton btn_back;
    private final ApiService api = ApiClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.preview_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");

        wbPreview = findViewById(R.id.wbPreview);
        WebSettings webSettings = wbPreview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setUpWebView();
        loadWebViewPreview(bookId);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadWebViewPreview(String bookId) {
        Call<BookAPI> call = api.getBook(bookId);
        call.enqueue(new Callback<BookAPI>() {
            @Override
            public void onResponse(Call<BookAPI> call, Response<BookAPI> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookAPI bookAPI = response.body();
                    String link = bookAPI.getFormats().getTextHtml();

                    link = link.replace("http://", "https://");
                    wbPreview.loadUrl(link);
                }
                else {
                    Log.d("Preview Activity", "Load web view error");
                }
            }

            @Override
            public void onFailure(Call<BookAPI> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setUpWebView() {
        WebSettings webSettings = wbPreview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // nen webview mau den
        wbPreview.setBackgroundColor(Color.BLACK);

        wbPreview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // hien thi content tren webview mau trang
                final String css = " * { " +
                        "     background-color: black !important; " +
                        "     color: white !important; " +
                        " } ";
                final String js = "javascript:(function() {" +
                        "  var parent = document.getElementsByTagName('head').item(0);" +
                        "  var style = document.createElement('style');" +
                        "  style.type = 'text/css';" +
                        "  style.innerHTML = '" + css.replace("'", "\\'") + "';" +
                        "  parent.appendChild(style);" +
                        "})()";
                view.loadUrl(js);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("WebView", "Lỗi tải trang: " + error.getDescription());
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}
