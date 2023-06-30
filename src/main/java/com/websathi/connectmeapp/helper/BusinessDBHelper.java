package com.websathi.connectmeapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.websathi.connectmeapp.model.business.Business;

public class BusinessDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "busines";

    private static final int DATABASE_VERSION = 1;

    private static final String Business_BOOKMARK_TABLE = "business_bookmark";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS business_bookmark";

    private static final String BUSINESS_TABLE_QUERY = "CREATE TABLE business_bookmark( id INTEGER PRIMARY KEY AUTOINCREMENT , " +
            "name VARCHAR, street VARCHAR, description VARCHAR, image varchar)";



    public BusinessDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUSINESS_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long insert(Business business) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", business.name);
        contentValues.put("street", business.location.street);
        contentValues.put("description", business.description);
//        contentValues.put("image", business.photos);

        return sqLiteDatabase.insert(Business_BOOKMARK_TABLE, null, contentValues);
    }
}
