package com.example.bookshelf.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookshelf.database.models.SearchHistory;

import java.util.List;

@Dao
public interface SearchDao {
    @Insert
    void insert(SearchHistory search);

    @Query("select * from SearchHistory")
    List<SearchHistory> getAll();
}
