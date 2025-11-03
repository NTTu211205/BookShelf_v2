package com.example.bookshelf.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "SearchHistory")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="content")
    private String content;

    public SearchHistory(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Ignore
    public SearchHistory(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
