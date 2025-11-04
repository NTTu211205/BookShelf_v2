package com.example.bookshelf.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookshelf.database.dao.BookDao;
import com.example.bookshelf.database.dao.SearchDao;
import com.example.bookshelf.database.models.BookDB;
import com.example.bookshelf.database.models.SearchHistory;

@Database(
        entities = {
                SearchHistory.class, BookDB.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "BOOK_SHELF";
    private static volatile AppDatabase instance;



    public abstract BookDao bookDao();
    public abstract SearchDao searchDao();



    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
                    return instance;
                }
            }
        }
        return instance;
    }
}
