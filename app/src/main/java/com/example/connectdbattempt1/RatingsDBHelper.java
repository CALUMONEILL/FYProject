package com.example.connectdbattempt1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RatingsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dBhearingprofile";
    private static final int DATABASE_VERSION = 1;

    public static final String ratings = "ratings";
    public static final String rating = "rating";

    public RatingsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Originally retrieved code similar to this for different functionality from ChatGPT
    //Wrote this code myself but attaching the ChatGPT link for reference:
    // https://chat.openai.com/share/da5fee58-e065-480d-a0eb-ba8999d76251
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createRatingsTable = "CREATE TABLE ratings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "rating TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createRatingsTable);
    }

    public void clearResponsesTable(String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ratings, null, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS responses");

        onCreate(sqLiteDatabase);
    }
}