package com.example.bookshelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookshelf.BookClickedActivity;
import com.example.bookshelf.R;
import com.example.bookshelf.api.models.BookAPI;

import java.util.List;

public class BookAPiAdapter extends RecyclerView.Adapter<PicksHolder> {
    String category;
    List<BookAPI> books;
    Context context;

    public BookAPiAdapter(List<BookAPI> books,Context context,String category) {
        this.books = books;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public PicksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_items, parent, false);
        return new PicksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicksHolder holder, int position) {
        BookAPI book = books.get(position);

        if (book.getTitle().length() <= 50) {
            holder.textView_title.setText(book.getTitle());
        }
        else {
            holder.textView_title.setText(book.getTitle().substring(0, 50) + "...");
        }

        String linkImage = null;
        if (book.getFormats() != null && book.getFormats().getImageJpeg() != null) {
            linkImage = book.getFormats().getImageJpeg();
        }
        if (linkImage != null) {
            linkImage = linkImage.replace("http://", "https://");
        }
        Glide.with(holder.itemView.getContext())
                .load(linkImage)
                .placeholder(R.drawable.icons8_loading_16)
                .error(R.drawable.non_thumbnail)
                .into(holder.imageView_book);

        holder.textView_author.setText(book.getAuthors());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookPosition = books.indexOf(book);

                Intent intent = new Intent(v.getContext(), BookClickedActivity.class);
                intent.putExtra("bookId", book.getId());
                intent.putExtra("pookPosition", bookPosition);
                intent.putExtra("categoryName", category);
                v.getContext().startActivity(intent);
//                ((Activity) v.getContext()).finish();
            }
        });
    }

    public void addBooks(List<BookAPI> listBooks) {
        int oldSize = books.size();
        books.addAll(listBooks);
        notifyItemRangeInserted(oldSize, listBooks.size());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
