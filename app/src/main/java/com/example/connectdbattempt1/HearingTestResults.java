package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HearingTestResults extends AppCompatActivity {

    private com.example.connectdbattempt1.ResponsesDBHelper dbHelper;

    FloatingActionButton btnHome;
    TextView txtFeedback1;
    Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test_results);

        dbHelper = new ResponsesDBHelper(this);
        //dbHelper.clearResultsTable("results");

        btnHome = findViewById(R.id.btnHome);
        txtFeedback1 = findViewById(R.id.txtFeedback);
        btnMore = findViewById(R.id.btnMore);

        // Adapted and implemented code from this video: https://www.youtube.com/watch?v=dm-jan0YORg
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
            }
        });

        //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
        int yesCount = countOccurrences("Yes");
        int noCount = countOccurrences("No");

        String firstEntry = getFirstEntry();
        String sixthEntry = getSixthEntry();

        Toast.makeText(HearingTestResults.this, firstEntry, Toast.LENGTH_SHORT).show();

        //Simple if else if statements - my own work
        if (yesCount == 6) {
            txtFeedback1.setText("Your results suggest that it is likely that you do not have any hearing issues.");
        } else if (yesCount == 0) {
            txtFeedback1.setText("Your results suggest that you may have some hearing issues.");
        } else if (yesCount > 3 && yesCount <= 6) {
            txtFeedback1.setText("Your results suggest that it is unlikely that you have any hearing issues.");
        } else if (yesCount > 0 && yesCount <= 3) {
            txtFeedback1.setText("Your results suggest that you may have some hearing issues.");
        }

        //Toast.makeText(this, "Yes count: " + yesCount + ", No count: " + noCount, Toast.LENGTH_SHORT).show();
        insertResult(yesCount);
        insertLowFreq(firstEntry);
        insertHighFreq(sixthEntry);
    }

    //Adapated from ChatGPT https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    public void insertResult(int yesCount) {
        //Insert data in database for results
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("result", yesCount);

        long newRowId = sqLiteDatabase.insert("results", null, values);

        if (newRowId != -1) {
            Toast.makeText(HearingTestResults.this, "Submitted!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("RESULTS", "Result not submitting to dB");
            Toast.makeText(HearingTestResults.this, "Not submitted", Toast.LENGTH_SHORT).show();
        }
    }

    //Adapted from ChatGPT https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    public void insertLowFreq(String sixthEntry) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ROWID FROM results WHERE lowFreq IS NULL LIMIT 1", null);

        long rowId = -1;
        if (cursor.moveToFirst()) {

            rowId = cursor.getLong(0);
        }
        cursor.close();

        if (rowId != -1) {

            ContentValues values = new ContentValues();
            values.put("lowFreq", sixthEntry);

            int rowsAffected = sqLiteDatabase.update("results", values, "ROWID = ?", new String[]{String.valueOf(rowId)});

            if (rowsAffected > 0) {
                Toast.makeText(HearingTestResults.this, "Updated first null lowFreq column!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("RESULTS", "Result not updating in dB");
                Toast.makeText(HearingTestResults.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        } else {

            ContentValues values = new ContentValues();
            values.put("lowFreq", sixthEntry);

            long newRowId = sqLiteDatabase.insert("results", null, values);

            if (newRowId != -1) {
                Toast.makeText(HearingTestResults.this, "Inserted new row!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("RESULTS", "Result not inserting into dB");
                Toast.makeText(HearingTestResults.this, "Insert failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Adapated from ChatGPT https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    public void insertHighFreq(String firstEntry) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ROWID FROM results WHERE highFreq IS NULL LIMIT 1", null);

        long rowId = -1;
        if (cursor.moveToFirst()) {

            rowId = cursor.getLong(0);
        }
        cursor.close();

        if (rowId != -1) {

            ContentValues values = new ContentValues();
            values.put("highFreq", firstEntry);

            int rowsAffected = sqLiteDatabase.update("results", values, "ROWID = ?", new String[]{String.valueOf(rowId)});

            if (rowsAffected > 0) {
                Toast.makeText(HearingTestResults.this, "Updated first null highFreq column!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("RESULTS", "Result not updating in dB");
                Toast.makeText(HearingTestResults.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        } else {

            ContentValues values = new ContentValues();
            values.put("highFreq", firstEntry);

            long newRowId = sqLiteDatabase.insert("results", null, values);

            if (newRowId != -1) {
                Toast.makeText(HearingTestResults.this, "Inserted new row!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("RESULTS", "Result not inserting into dB");
                Toast.makeText(HearingTestResults.this, "Insert failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    // Had to adapt calling the database helper and how the application accesses data - ChatGPT code was not entirely correct
    private int countOccurrences(String response) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.response};
        String selection = dbHelper.response + " = ?";
        String[] selectionArgs = {response};

        Cursor cursor = db.query(
                dbHelper.responses,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    //Adapted from ChatGPT https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    private String getFirstEntry() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.response};

        Cursor cursor = db.query(
                dbHelper.responses,
                projection,
                null,
                null,
                null,
                null,
                "id ASC",
                "1"
        );

        String firstEntry = null;
        if (cursor.moveToFirst()) {
            firstEntry = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.response));
        }
        cursor.close();
        db.close();

        return firstEntry;

    }

    private String getSixthEntry() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.response};

        Cursor cursor = db.query(
                dbHelper.responses,
                projection,
                null,
                null,
                null,
                null,
                "id ASC",
                "5, 1"
        );

        String sixthEntry = null;
        if (cursor.moveToFirst()) {
            sixthEntry = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.response));
        }
        cursor.close();
        db.close();

        return sixthEntry;

    }
}



















// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)