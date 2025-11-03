package com.example.bookshelf.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookshelf.ShowAllBookOfCate;
import com.example.bookshelf.R;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>{
    List<String> list;

    public CategoriesAdapter(List<String> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_items, parent, false);
        return new CategoriesHolder(view1);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        String string = list.get(position);
        holder.tvCateName.setText(string);

        holder.tvCateName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(Color.parseColor("#606060"));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundColor(Color.parseColor("#161616"));
                        break;
                }
                return false;
            }
        });

        holder.tvCateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowAllBookOfCate.class);
                intent.putExtra("categoryName", string);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class CategoriesHolder extends RecyclerView.ViewHolder {
        TextView tvCateName;
        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            tvCateName = itemView.findViewById(R.id.tvCateName);
        }
    }
}
