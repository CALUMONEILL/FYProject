package com.example.connectdbattempt1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ResponsesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dBhearingprofile";
    private static final int DATABASE_VERSION = 10;

    public static final String responses = "responses";
    public static final String response = "response";

    public static final String ratings = "ratings";

    public static final String rating = "rating";

    public static final String results = "results";
    public static final String result = "results";

    public ResponsesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Originally retrieved code similar to this for different functionality from ChatGPT
    //Wrote this code myself but attaching the ChatGPT link for reference:
    // https://chat.openai.com/share/da5fee58-e065-480d-a0eb-ba8999d76251

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createResponsesTable = "CREATE TABLE responses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "response TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createResponsesTable);

        String createRatingsTable = "CREATE TABLE ratings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "rating REAL,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createRatingsTable);

        String createResultsTable = "CREATE TABLE results ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "result INT,"
                + "lowFreq STRING,"
                + "highFreq STRING,"
                + "rating STRING,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createResultsTable);

        // Survey table creation code retrieved from ChatGPT https://chat.openai.com/share/1d089765-3568-4cf1-8641-e87db81c1ead
        String createSurveyTable = "CREATE TABLE SurveyResponses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Answer1 STRING,"
                + "Answer2 STRING,"
                + "Answer3 STRING,"
                + "Answer4 STRING)";
        sqLiteDatabase.execSQL(createSurveyTable);

    }


    public void clearResponsesTable(String response) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(responses, null, null);
    }

    public void clearResultsTable(String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(results, null, null);
    }

    public void clearRatingsTable(String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ratings, null, null);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ratings");

        onCreate(sqLiteDatabase);
    }

}