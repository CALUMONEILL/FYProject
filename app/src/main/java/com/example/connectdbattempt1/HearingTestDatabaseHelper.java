package com.example.connectdbattempt1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HearingTestDatabaseHelper extends SQLiteOpenHelper {
    // Retrieved from ChatGPT and adapted based on my own knowledge of SQL database creation

    private static final String DATABASE_NAME = "dBhearphones";
    private static final int DATABASE_VERSION = 9;

    public HearingTestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE ratings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user TEXT,"
                + "Hz TEXT,"
                + "rating TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTableQuery);

    }

    public void clearTable(String ratings) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ratings, null, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ratings");

        onCreate(db);
    }

}

// https://chat.openai.com/share/da5fee58-e065-480d-a0eb-ba8999d76251