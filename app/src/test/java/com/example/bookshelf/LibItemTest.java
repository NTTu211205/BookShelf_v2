package com.example.bookshelf;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.bookshelf.models.LibItem;

public class LibItemTest {

    @Test
    public void testLibItemCreation() {
        // Chuẩn bị dữ liệu mẫu: ID ảnh và phần trăm tiến độ đọc
        int imageId = 999;
        String percent = "45%";

        // Tạo đối tượng LibItem
        LibItem item = new LibItem(imageId, percent);

        // Kiểm tra xem đối tượng có lưu đúng giá trị không
        assertEquals(imageId, item.getImageResId());
        assertEquals("45%", item.getPercent());

        // In ra console để báo hiệu test đã chạy xong
        System.out.println("Test passed!");
    }
}