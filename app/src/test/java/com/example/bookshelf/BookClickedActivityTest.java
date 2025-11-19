package com.example.bookshelf;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class) // Báo cho JUnit biết cần dùng Robolectric để giả lập Android
@Config(sdk = 33) // Giả lập chạy trên Android 13 (API 33)
public class BookClickedActivityTest {

    @Test
    public void testActivityReceivesIntentData() {
        // --- 1. ARRANGE (Chuẩn bị) ---
        // Tạo một Intent giả để chuyển màn hình
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), BookClickedActivity.class);
        // Đóng gói dữ liệu "bookId" vào intent (giả lập việc click từ màn hình danh sách sách)
        intent.putExtra("bookId", "12345");

        // --- 2. ACT (Thực hiện) ---
        // Dùng ActivityScenario để khởi động Activity với intent đã tạo ở trên
        try (ActivityScenario<BookClickedActivity> scenario = ActivityScenario.launch(intent)) {

            // Thực hiện các thao tác bên trong Activity sau khi nó đã chạy
            scenario.onActivity(activity -> {

                // --- 3. ASSERT (Kiểm tra kết quả) ---
                // Kiểm tra xem Activity có được tạo thành công không (không bị null)
                assertNotNull(activity);

                // Kiểm tra các thành phần giao diện (UI) quan trọng có xuất hiện không
                assertNotNull("Nút Back phải tồn tại", activity.findViewById(R.id.btn_back));
                assertNotNull("Nút Read Sample phải tồn tại", activity.findViewById(R.id.btn_read_sample));

                // Kiểm tra xem Activity có nhận đúng "bookId" là "12345" từ Intent không
                assertEquals("12345", activity.getIntent().getStringExtra("bookId"));
            });
        }
    }
}