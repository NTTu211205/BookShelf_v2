package com.example.bookshelf;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.bookshelf.models.Book;

public class BookItemTest {

    @Test
    public void testBookConstructorAndGetters() {
        // Chuẩn bị dữ liệu mẫu
        int fakeImageId = 12345; // Giả lập ID của hình ảnh (trong Android R.drawable.x cũng là int)
        String title = "Dế Mèn Phiêu Lưu Ký";
        String author = "Tô Hoài";

        // Khởi tạo đối tượng Book với dữ liệu mẫu
        Book book = new Book(fakeImageId, title, author);

        // Kiểm tra xem các hàm get (getters) có trả về đúng dữ liệu đã nạp vào không
        assertEquals("ID ảnh bìa phải khớp", fakeImageId, book.getCoverImageResource());
        assertEquals("Tên sách phải khớp", title, book.getTitle());
        assertEquals("Tác giả phải khớp", author, book.getAuthor());
    }

    @Test
    public void testBookWithNullValues() {
        // Test trường hợp ngoại lệ: Khi dữ liệu đầu vào bị thiếu (null)
        // Mục đích: Đảm bảo ứng dụng không bị crash nếu thiếu tên sách hoặc tên tác giả
        Book book = new Book(0, null, null);

        assertNull(book.getTitle());
        assertNull(book.getAuthor());
    }
}