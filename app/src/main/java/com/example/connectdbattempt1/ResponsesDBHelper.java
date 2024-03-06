package com.example.connectdbattempt1;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ResponsesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dBhearingprofile";
    private static final int DATABASE_VERSION = 12;

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

        String createConfidenceTable = "CREATE TABLE confidence ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "confidence REAL,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createConfidenceTable);

        String createResultsTable = "CREATE TABLE results ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "result INT,"
                + "lowFreq STRING,"
                + "highFreq STRING,"
                + "rating REAL,"
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

        String createAnswersTable = "CREATE TABLE answers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "answer TEXT,"
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createAnswersTable);

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

    public double getSecondMostRecentConfidence() {
        double secondMostRecentConfidence = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();

        // Selecting all entries ordered by date in descending order
        Cursor cursor = db.rawQuery("SELECT confidence FROM confidence ORDER BY date DESC", null);

        try {
            // Move cursor to the second entry
            if (cursor.moveToPosition(1)) {
                // Retrieving confidence value from the second entry
                secondMostRecentConfidence = cursor.getDouble(cursor.getColumnIndexOrThrow("confidence"));
            } else {
                Log.e("ResponsesDBHelper", "No second entry found");
            }
        } catch (Exception e) {
            Log.e("ResponsesDBHelper", "Error: " + e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        return secondMostRecentConfidence;
    }
}

