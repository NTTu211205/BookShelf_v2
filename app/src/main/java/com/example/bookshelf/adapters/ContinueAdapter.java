package com.example.bookshelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookshelf.ReadOffline;
import com.example.bookshelf.database.models.BookDB;
import com.example.bookshelf.models.ItemContinueReading;
import com.example.bookshelf.R;

import java.io.File;
import java.util.List;

public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ContinueViewHolder> {

    private Context context;
    private List<BookDB> itemList;

    public ContinueAdapter(Context context, List<BookDB> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ContinueAdapter.ContinueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.nearest_saving_items, parent, false);
        return new ContinueViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueViewHolder holder, int position) {
        BookDB item = itemList.get(position);

        String title = item.getTitle();
        if (title != null && title.length() > 0) {
            int titleEndIndex = Math.min(title.length(), 10);
            holder.textTitle.setText(title.substring(0, titleEndIndex) + (title.length() > 10 ? "..." : ""));
        } else {
            holder.textTitle.setText("N/A"); // Hoặc chuỗi mặc định khác
        }

        // --- Sửa lỗi tại Dòng 44: Kiểm tra độ dài tác giả ---
        String authors = item.getAuthors();
        if (authors != null && authors.length() > 0) {
            int authorEndIndex = Math.min(authors.length(), 10);
            holder.textAuthor.setText(authors.substring(0, authorEndIndex) + (authors.length() > 10 ? "..." : ""));
        } else {
            holder.textAuthor.setText("N/A"); // Hoặc chuỗi mặc định khác
        }

        holder.textPercent.setText("20%");

        // load anh bia sach
        File file = new File(context.getFilesDir(), item.getLinkThumbnail());
        Glide.with(context)
                .load(file)
                .placeholder(R.drawable.icons8_loading_16)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadOffline.class);
                intent.putExtra("fileName", item.getLinkBook());
                intent.putExtra("title", item.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ContinueViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle, textAuthor, textPercent;

        public ContinueViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book);
            textTitle = itemView.findViewById(R.id.textView_title);
            textAuthor = itemView.findViewById(R.id.textView_author);
            textPercent = itemView.findViewById(R.id.textView_percent);
        }
    }
}
