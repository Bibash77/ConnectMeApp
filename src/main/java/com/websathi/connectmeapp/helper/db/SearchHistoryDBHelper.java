package com.websathi.connectmeapp.helper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "search_history.db";
    private static final int DATABASE_VERSION = 1;

    public SearchHistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE search_history (_id INTEGER PRIMARY KEY, query TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database if needed
    }


    public void insertSearchQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete excess records if necessary
        long rowCount = DatabaseUtils.queryNumEntries(db, "search_history");
        if (rowCount >= 10) {
            long rowsToDelete = rowCount - 9; // Keep the latest 10 queries
            db.delete("search_history", "_id <= ?", new String[]{String.valueOf(rowsToDelete)});
        }

        // Insert the new search query
        ContentValues values = new ContentValues();
        values.put("query", query);
        db.insert("search_history", null, values);
        db.close();
    }


    public List<String> getLatestSearchQueries() {
        List<String> searchHistory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT query FROM search_history ORDER BY timestamp DESC LIMIT 10", null);

        if (cursor.moveToFirst()) {
            do {
                String query = cursor.getString(cursor.getColumnIndex("query"));
                searchHistory.add(query);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return searchHistory;
    }
}
