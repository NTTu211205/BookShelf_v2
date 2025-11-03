package com.example.bookshelf.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.R;

public class PicksHolder extends RecyclerView.ViewHolder {
    ImageView imageView_book;
    TextView textView_title, textView_author;
    public PicksHolder(@NonNull View itemView) {
        super(itemView);
        imageView_book = itemView.findViewById(R.id.imageView_book);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_author = itemView.findViewById(R.id.textView_author);

    }
}
