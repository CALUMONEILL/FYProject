package com.example.connectdbattempt1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HearingTestDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dBhearphones";
    private static final int DATABASE_VERSION = 3;

    public HearingTestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table(s)
        String createTableQuery = "CREATE TABLE ratings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "rating TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ratings");

        onCreate(db);
    }

}