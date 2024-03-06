package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView imgHearing;
    ImageView imgConfidence;
    TextView txtDesc2;
    TextView txtRatingNumber;
    Button btnAll;

    private boolean shouldChangeImages = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("changeImages")) {
            shouldChangeImages = intent.getBooleanExtra("changeImages", true);
        }


        dbHelper = new ResponsesDBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        txtScoreNumber = findViewById(R.id.txtScoreNumber);

        txtStruggle = findViewById(R.id.txtStruggle);
        txtRatingNumber = findViewById(R.id.txtRatingNumber);
        btnAll = findViewById(R.id.btnAll);
        btnHome = findViewById(R.id.btnHome);
        imgHearing = findViewById(R.id.imgHearing);
        imgConfidence = findViewById(R.id.imgConfidence);
        txtFeedback1 = findViewById(R.id.txtAnalytics);

        fetchResults();
        fetchRatings();
        fetchMostRecentFreqs();
        fetchAndCalculateAverageRating();

        Double averageRating = fetchAndCalculateAverageRating();



        if (shouldChangeImages) {
            insertAverageRating(averageRating);


        }

        compareConfidenceWithRating(averageRating);


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
                "2"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int currentResult = cursor.getInt(cursor.getColumnIndexOrThrow("result"));

                    if (cursor.moveToNext()) {
                        int previousResult = cursor.getInt(cursor.getColumnIndexOrThrow("result"));

                        if (currentResult > previousResult) {
                            //Log.d("Result0000", "Current result is higher than the previous one");
                            imgHearing.setImageResource(R.drawable.up);
                        } else if (currentResult < previousResult) {
                            //Log.d("Result0000", "Current result is lower than the previous one");
                            imgHearing.setImageResource(R.drawable.down);
                        } else {
                            //Log.d("Result0000", "Current result is equal to the previous one");
                            imgHearing.setImageResource(R.drawable.same);
                        }
                    } else {
                        //Log.d("Result0000", "Only one result found, cannot compare with previous result");

                    }

                    Log.d("Result0000", "Most Recent Result: " + currentResult);

                    txtScoreNumber.setText(" " + currentResult + "/6");

                } else {
                    Log.d("Result", "No results found");
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void fetchRatings() {
        String[] projection = {
                "rating"
        };

        String sortOrder = "date DESC";

        Cursor cursor = sqLiteDatabase.query(
                "ratings",
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                "2"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    double currentResult = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));

                    if (cursor.moveToNext()) {
                        double previousResult = cursor.getInt(cursor.getColumnIndexOrThrow("rating"));

                        if (currentResult > previousResult) {
                            //Log.d("Result0000", "Current result is higher than the previous one");
                            imgConfidence.setImageResource(R.drawable.up);
                        } else if (currentResult < previousResult) {
                            //Log.d("Result0000", "Current result is lower than the previous one");
                            imgConfidence.setImageResource(R.drawable.down);
                        } else {
                            //Log.d("Result0000", "Current result is equal to the previous one");
                            imgConfidence.setImageResource(R.drawable.same);
                        }
                    } else {
                        //Log.d("Result0000", "Only one result found, cannot compare with previous result");

                    }

                    Log.d("Result0000", "Most Recent Result: " + currentResult);

                    txtRatingNumber.setText(" " + currentResult + "/5");

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
    private double fetchAndCalculateAverageRating() {
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
                    double roundedAverageRating = Math.ceil(averageRating * 10) / 10;
                    String formattedAverageRating = String.format("%.1f", averageRating);
                    txtRatingNumber.setText(" " + formattedAverageRating + "/5");

                    return roundedAverageRating;

                } else {

                    txtRatingNumber.setText(" ~/6");
                    Log.d("RATINGS", "Ratings crashed");
                }
            } finally {
                cursor.close();
            }
        }

        return 0.0;
    }

    public void compareConfidenceWithRating(double averageRating) {
        //ResponsesDBHelper dbHelper = new ResponsesDBHelper();
        double secondMostRecentConfidence = dbHelper.getSecondMostRecentConfidence();

        if (secondMostRecentConfidence > averageRating) {
            // Second most recent confidence is greater than averageRating
            Log.d("StatsWahey", "Second most recent confidence is greater than average rating.");
            imgConfidence.setImageResource(R.drawable.down);
        } else if (secondMostRecentConfidence < averageRating) {
            // Second most recent confidence is lesser than averageRating
            Log.d("StatsWahey", "Second most recent confidence is lesser than average rating.");
            imgConfidence.setImageResource(R.drawable.up);
        } else {
            // Second most recent confidence is equal to averageRating
            Log.d("StatsWahey", "Second most recent confidence is equal to average rating.");
            imgConfidence.setImageResource(R.drawable.same);
        }
    }


    private void insertAverageRating(double averageRating) {
        ContentValues values = new ContentValues();
        values.put("confidence", averageRating);

        long newRowId = sqLiteDatabase.insert("confidence", null, values);

        if (newRowId != -1) {
            Log.d("DATABASE", "Average rating inserted successfully");
        } else {
            Log.d("DATABASE", "Failed to insert average rating");
        }
    }


}


// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)