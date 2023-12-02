package com.websathi.connectmeapp.helper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.websathi.connectmeapp.BL.SearchConfig;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Location;

import java.util.ArrayList;

public class SearchSettingDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "busines";

    private static final int DATABASE_VERSION = 2;
    private static final String DEFAULT_SETTING_NAME = "DEFAULT";

    private static final String SEARCH_SETTING_TABLE = "search_setting";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS search_setting";

    private static final String SEARCH_TABLE_CREATE_QUERY = "CREATE TABLE search_setting(setting_name varchar , " +

            " radius varchar, rating varchar, categories VARCHAR, latitude varchar, longitude varchar, formatted_address varchar)";

    public SearchSettingDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SEARCH_TABLE_CREATE_QUERY);
        insertDefaultValues(db);
    }

    public void insertDefaultValues(SQLiteDatabase sqLiteDatabase) {
        final SearchConfig searchConfig = new SearchConfig();
        searchConfig.setSettingName(DEFAULT_SETTING_NAME);
        searchConfig.setCategories("COLLEGE");
        searchConfig.setRadius("500");
        searchConfig.setRating("3");
        insert(searchConfig, sqLiteDatabase);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insert(SearchConfig searchConfig, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("radius", searchConfig.getRadius());
        contentValues.put("setting_name", searchConfig.getSettingName());
        contentValues.put("rating", searchConfig.getRating());
        contentValues.put("categories", searchConfig.getCategories());
        contentValues.put("latitude", searchConfig.getLatitude()  );
        contentValues.put("longitude", searchConfig.getLongitude());
        long rowId =  db.insert(SEARCH_SETTING_TABLE, null, contentValues);
        System.out.println(rowId);
    }

    public void updateValue(String key, String value) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        getAllSearchSetting(sqLiteDatabase);

        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);

        // Check if a row with the specified key already exists
        String whereClause = "setting_name = ?";
        String[] whereArgs = {DEFAULT_SETTING_NAME};
        Cursor cursor = sqLiteDatabase.query(SEARCH_SETTING_TABLE, null, whereClause, whereArgs, null, null, null);
        sqLiteDatabase.update(SEARCH_SETTING_TABLE, contentValues, whereClause, whereArgs);
        // Close the cursor and database connection
        cursor.close();
        sqLiteDatabase.close();
    }

    public SearchConfig getDefaultValues() {
        SQLiteDatabase db = getReadableDatabase();
        SearchConfig defaultValues = new SearchConfig();

        // Specify the columns you want to retrieve
        String[] columns = {"radius", "rating", "categories", "latitude", "longitude", "formatted_address"};

        // Specify the selection criteria (in this case, where setting_name is DEFAULT)
        String selection = "setting_name = ?";
        String[] selectionArgs = {DEFAULT_SETTING_NAME};

        // Query the database
        Cursor cursor = db.query(SEARCH_SETTING_TABLE, columns, selection, selectionArgs, null, null, null);

        // Check if there's at least one row
        if (cursor.moveToFirst()) {
            defaultValues.setRadius(cursor.getString(cursor.getColumnIndex("radius")));
            defaultValues.setRating(cursor.getString(cursor.getColumnIndex("rating")));
            defaultValues.setCategories(cursor.getString(cursor.getColumnIndex("categories")));
            defaultValues.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
            defaultValues.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
            defaultValues.setFormattedAddress(cursor.getString(cursor.getColumnIndex("formatted_address")));
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return defaultValues;
    }


    public void getAllSearchSetting(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SEARCH_SETTING_TABLE, null);
        ArrayList<Business> businesses = new ArrayList<>();

        while (cursor.moveToNext()) {
            System.out.println(cursor.getString(0));
        }
    }


}
