package com.example.bookshelf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookshelf.models.ItemContinueReading;
import com.example.bookshelf.R;

import java.util.List;

public class ContinueAdapter extends RecyclerView.Adapter<ContinueAdapter.ContinueViewHolder> {

    private Context context;
    private List<ItemContinueReading> itemList;

    public ContinueAdapter(Context context, List<ItemContinueReading> itemList) {
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
        ItemContinueReading item = itemList.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.textTitle.setText(item.getTitle());
        holder.textAuthor.setText(item.getAuthor());
        holder.textPercent.setText(item.getPercent());
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
