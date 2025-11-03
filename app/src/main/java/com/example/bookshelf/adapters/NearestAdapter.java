package com.example.bookshelf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.R;
import com.example.bookshelf.models.NearestRead; // Dùng model NearestRead

import java.util.List;

public class NearestAdapter extends RecyclerView.Adapter<NearestAdapter.NearestViewHolder> {

    private Context context;
    private List<NearestRead> itemList;

    public NearestAdapter(Context context, List<NearestRead> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public NearestAdapter.NearestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.nearest_read_items, parent, false);
        return new NearestViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull NearestViewHolder holder, int position) {
        NearestRead item = itemList.get(position);

        // Ánh xạ dữ liệu từ NearestRead
        holder.imageView.setImageResource(item.getImgResources());
        holder.textAuthor.setText(item.getAuthor());
        holder.textTitle.setText(item.getTitle());
        holder.textType.setText(item.getType());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class NearestViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textAuthor, textType, textTitle;

        public NearestViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID theo nearest_read_items
            imageView = itemView.findViewById(R.id.imageView_book);
            textAuthor = itemView.findViewById(R.id.tvAuthor);
            textTitle = itemView.findViewById(R.id.tvTitle);
            textType = itemView.findViewById(R.id.tvBook);
        }
    }
}