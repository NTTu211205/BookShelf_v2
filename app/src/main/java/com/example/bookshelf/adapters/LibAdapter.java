package com.example.bookshelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//import com.folioreader.FolioReader;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookshelf.ReadOffline;
import com.example.bookshelf.database.models.BookDB;
import com.example.bookshelf.models.LibItem;
import com.example.bookshelf.R;

import java.io.File;
import java.util.List;

import nl.siegmann.epublib.epub.EpubReader;

public class LibAdapter extends RecyclerView.Adapter<LibAdapter.LibViewHolder> {

    private Context context;
    private List<BookDB> itemList;

    public LibAdapter(Context context, List<BookDB> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public LibViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.lib_items, parent, false);
        return new LibViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull LibViewHolder holder, int position) {
        BookDB item = itemList.get(position);
        File file = new File(context.getFilesDir(), item.getLinkThumbnail());

        Glide.with(context)
                .load(file)
                .placeholder(R.drawable.icons8_loading_16)
                .into(holder.imageView);
        holder.textPercent.setText("20%");
        holder.tvTitle.setText(item.getTitle().substring(0, 10) + "...");
        holder.markAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textPercent.setText("Read");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(context.getFilesDir(), item.getLinkBook());

                if (file.exists()) {
                    Intent intent = new Intent(v.getContext(), ReadOffline.class);
                    intent.putExtra("fileName", item.getLinkBook());
                    intent.putExtra("title", item.getTitle());
                    context.startActivity(intent);
                }
                else {
                    Log.d("File to read", "File not exist");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class LibViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textPercent, tvTitle;
        ImageView markAsRead;

        public LibViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book);
            textPercent = itemView.findViewById(R.id.textView_percent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            markAsRead = itemView.findViewById(R.id.imgView_mark_as_read);
        }
    }
}
