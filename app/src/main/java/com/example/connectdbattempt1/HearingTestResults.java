package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HearingTestResults extends AppCompatActivity {

    private com.example.connectdbattempt1.ResponsesDBHelper dbHelper;

    Button btnHome;
    TextView txtFeedback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test_results);

        startTimer();

        dbHelper = new ResponsesDBHelper(this);

        btnHome = findViewById(R.id.btnHome);
        txtFeedback1 = findViewById(R.id.txtFeedback1);

        // Adapted and implemented code from this video: https://www.youtube.com/watch?v=dm-jan0YORg
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
        int yesCount = countOccurrences("Yes");
        int noCount = countOccurrences("No");

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
    }

    public void insertResult(int yesCount) {
        //Insert data in database for results
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("result", yesCount);

        //db.insert("TABLE_RESPONSE", null, null);

        long newRowId = sqLiteDatabase.insert("results", null, values);

        if (newRowId != -1) {
            Toast.makeText(HearingTestResults.this, "Submitted!", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("RESULTS", "Result not submitting to dB");
            Toast.makeText(HearingTestResults.this, "Not submitted", Toast.LENGTH_SHORT).show();
        }
    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/a8e9d077-a933-46be-9eb8-57c0c9fbb508
    // Had to adapt calling the database helper and how the application accesses data - ChatGPT code was not entirely correct
    private int countOccurrences (String response){
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

    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            // 5 second countdown
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), TestConfidence.class);
                startActivity(intent);
            }
        };

        countDownTimer.start();
    }
}



















// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)