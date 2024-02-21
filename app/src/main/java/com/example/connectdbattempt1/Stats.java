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

public class Stats extends AppCompatActivity {

    private ResponsesDBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    FloatingActionButton btnHome;
    TextView txtFeedback1;
    TextView txtScoreNumber;
    TextView txtStruggle;

    TextView txtDesc2;
    TextView txtRatingNumber;

    Button btnAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        dbHelper = new ResponsesDBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        txtScoreNumber = findViewById(R.id.txtScoreNumber);
        txtStruggle = findViewById(R.id.txtStruggle);
        txtRatingNumber = findViewById(R.id.txtRatingNumber);
        btnAll = findViewById(R.id.btnAll);
        btnHome = findViewById(R.id.btnHome);

        fetchResults();
        fetchMostRecentFreqs();
        fetchAndCalculateAverageRating();

        txtFeedback1 = findViewById(R.id.txtAnalytics);

        // Adapted and implemented code from this video: https://www.youtube.com/watch?v=dm-jan0YORg
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), userData.class);
                startActivity(intent);
            }
        });
    }


    //Adapted from ChatGPT https://chat.openai.com/share/c4a3f9c1-407e-4f6d-aa64-3df288b7a4a9
    private void fetchResults() {
        String[] projection = {
                "result"
        };

        String sortOrder = "date DESC";

        Cursor cursor = sqLiteDatabase.query(
                "results",
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                "1"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int result = cursor.getInt(cursor.getColumnIndexOrThrow("result"));

                    Log.d("Result", "Most Recent Result: " + result);

                    txtScoreNumber.setText(" " + result + "/6");
                } else {

                    Log.d("Result", "No results found");
                }
            } finally {
                cursor.close();
            }
        }
    }

    //Adapted from ChatGPT https://chat.openai.com/share/c4a3f9c1-407e-4f6d-aa64-3df288b7a4a9
    private void fetchMostRecentFreqs() {
        String[] projection = {
                "lowFreq",
                "highFreq"
        };

        String sortOrder = "date DESC";

        Cursor cursor = sqLiteDatabase.query(
                "results",
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                "1"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    String lowFreq = cursor.getString(cursor.getColumnIndexOrThrow("lowFreq"));
                    String highFreq = cursor.getString(cursor.getColumnIndexOrThrow("highFreq"));


                    if (highFreq.equals("Yes")) {
                        if (lowFreq.equals("Yes")) {
                            // highFreq = Yes and lowFreq = Yes
                            txtStruggle.setText("You did not struggle with lower or higher frequencies");
                        } else {
                            // highFreq = Yes and lowFreq = No
                            txtStruggle.setText("You struggled with low frequencies");
                        }
                    } else {
                        if (lowFreq.equals("Yes")) {
                            // highFreq = No and lowFreq = Yes
                            txtStruggle.setText("You struggled with high frequencies");
                        } else {
                            // highFreq = No and lowFreq = No
                            txtStruggle.setText("You struggled with low and high frequencies");
                        }
                    }

                } else {
                    Log.d("Result", "No results found");
                }
            } finally {
                cursor.close();
            }
        }
    }

    //Adapted from ChatGPT https://chat.openai.com/share/c4a3f9c1-407e-4f6d-aa64-3df288b7a4a9
    private void fetchAndCalculateAverageRating() {
        String[] projection = {
                "rating"
        };

        Cursor cursor = sqLiteDatabase.query(
                "ratings",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            try {
                double totalRating = 0.0;
                int ratingCount = cursor.getCount();

                while (cursor.moveToNext()) {
                    double rating = cursor.getDouble(cursor.getColumnIndexOrThrow("rating"));
                    totalRating += rating;
                }

                if (ratingCount > 0) {
                    double averageRating = totalRating / ratingCount;
                    String formattedAverageRating = String.format("%.1f", averageRating);
                    txtRatingNumber.setText("  " + formattedAverageRating + "/5");

                } else {

                    txtRatingNumber.setText(" ~/6");
                    Log.d("RATINGS", "Ratings crashed");
                }
            } finally {
                cursor.close();
            }
        }
    }
}

// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)