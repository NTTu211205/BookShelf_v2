package com.example.bookshelf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.models.LibItem;
import com.example.bookshelf.R;

import java.util.List;

public class LibAdapter extends RecyclerView.Adapter<LibAdapter.LibViewHolder> {

    private Context context;
    private List<LibItem> itemList;

    public LibAdapter(Context context, List<LibItem> itemList) {
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
        LibItem item = itemList.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.textPercent.setText(item.getPercent());
        holder.markAsRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textPercent.setText("Read");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class LibViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textPercent;
        ImageView markAsRead;

        public LibViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book);
            textPercent = itemView.findViewById(R.id.textView_percent);
            markAsRead = itemView.findViewById(R.id.imgView_mark_as_read);
        }
    }
}
