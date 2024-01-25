package com.example.connectdbattempt1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ResponsesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dBhearingprofile";
    private static final int DATABASE_VERSION = 1;

    public ResponsesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableQuery = "CREATE TABLE responses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "response TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ratings");

        onCreate(sqLiteDatabase);
    }
}