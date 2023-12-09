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

        // Fetch the existing search history
        String currentSearchHistory = "";
        Cursor cursor = db.rawQuery("SELECT query FROM search_history LIMIT 1", null);
        if (cursor.moveToFirst()) {
            currentSearchHistory = cursor.getString(cursor.getColumnIndex("query"));
        }
        cursor.close();

        // Concatenate the new search query with the existing search history
        String newSearchHistory = currentSearchHistory + ", " + query;

        // Limit the length of the search history string (adjust the limit as needed)
        int maxLength = 255; // For example, limit to 255 characters
        if (newSearchHistory.length() > maxLength) {
            // Trim the excess from the beginning (FIFO approach)
            newSearchHistory = newSearchHistory.substring(newSearchHistory.length() - maxLength);
        }

        // Update the existing record with the new concatenated search history
        ContentValues values = new ContentValues();
        values.put("query", newSearchHistory);

        // Perform the update operation (assuming there's only one row in the table)
        int rowsAffected = db.update("search_history", values, null, null);

        // Check if the update was successful
        if (rowsAffected <= 0) {
            // If no rows were updated, insert a new record (as the table might be empty)
            values.put("query", newSearchHistory);
            db.insert("search_history", null, values);
        }

        // Close the database connection
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

    public String getSearchHistory() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Fetch the most recent search history
        String searchHistory = "";
        Cursor cursor = db.rawQuery("SELECT query FROM search_history ORDER BY _id DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            searchHistory = cursor.getString(cursor.getColumnIndex("query")); // Keep the column name as "query"
        }
        cursor.close();

        // Close the database connection
        db.close();

        return searchHistory;
    }

}
