package com.example.bookshelf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookshelf.database.models.BookDB;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(BookDB book);

    @Query("select * from Book")
    List<BookDB> getAll();

    @Query("select * from Book where id = :queryId")
    BookDB getBook(String queryId);

    @Query("delete from Book")
    void deleteAll();

    @Query("delete from Book where id = :id")
    void delete (String id);
}
