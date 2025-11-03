package com.example.bookshelf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView; // Đảm bảo đã import

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.models.BookCateItem;
import com.example.bookshelf.R;

import java.util.List;

public class BookCateAdapter extends RecyclerView.Adapter<BookCateAdapter.BookCateViewHolder> {

    private Context context;
    private List<BookCateItem> bookList;
//    private OnCategoryClickListener listener; // THÊM LẠI: Listener để xử lý click

    // --- 1. THÊM LẠI: Interface để gửi sự kiện click ---
//    public interface OnCategoryClickListener {
//        void onCategoryClick(BookCateItem item);
//    }

    // --- 2. SỬA: Cập nhật constructor để nhận listener ---
//    public BookCateAdapter(Context context, List<BookCateItem> bookList, OnCategoryClickListener listener) {
//        this.context = context;
//        this.bookList = bookList;
////        this.listener = listener; // Gán listener
//    }

    public BookCateAdapter(Context context, List<BookCateItem> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookCateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Dùng layout R.layout.items_featured_col (Giống code của bạn)
        View view = LayoutInflater.from(context).inflate(R.layout.items_featured_col, parent, false);
        return new BookCateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCateViewHolder holder, int position) {
        BookCateItem book = bookList.get(position);

        // Dùng các hàm model của bạn: getImageResId() và getType()
        holder.coverImageView.setImageResource(book.getImageResId());
        holder.textTitle.setText(book.getType());
        // holder.textLabel sẽ giữ nguyên "FEATURED COLLECTION" từ XML

        // --- 3. THÊM LẠI: Gán sự kiện click cho item ---
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onCategoryClick(book);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // ViewHolder của bạn (Giữ nguyên)
    public class BookCateViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView textTitle, textLabel;

        public BookCateViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.imageView_featured_books);
            textTitle = itemView.findViewById(R.id.textView_featured_title);
            textLabel = itemView.findViewById(R.id.textView_featured_label);
        }
    }
}