package com.example.connectdbattempt1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
// Media Player import for the beep button. Retrieved from ChatGPT.
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class HearingTest extends AppCompatActivity {

    // Defining my Media Player for the basic hearing test
    private MediaPlayer mediaPlayer;

    public Button btnPlay1;
    public Button btnPlay2;
    Button btnHome;
    Button btnSubmit;
    RatingBar rtbRate;
    private HearingTestDatabaseHelper HearingTestDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, R.raw.audio1);
        btnPlay1 = findViewById(R.id.btnPlay);

        //btnPlay1.setVisibility(View.VISIBLE);
        //btnPlay2.setVisibility(View.INVISIBLE);

        btnPlay2 = findViewById(R.id.btnPlay2);
        btnHome = findViewById(R.id.btnHome);
        rtbRate = findViewById(R.id.rtbRate);
        btnSubmit = findViewById(R.id.btnSubmit);

        HearingTestDatabaseHelper = new HearingTestDatabaseHelper(this);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rating = String.valueOf(rtbRate.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();

                saveDataToDatabase(rating);

                rtbRate.setRating(0);

                btnPlay1.setVisibility(View.INVISIBLE);
                btnPlay2.setVisibility(View.VISIBLE);


            }
        });

    }

    private void saveDataToDatabase(String rating) {
        SQLiteDatabase db = HearingTestDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("rating", rating);

        long newRowId = db.insert("ratings", null, values);

        // Handle the result as needed
        if (newRowId != -1) {
            Toast.makeText(this, "Submitted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not submitted", Toast.LENGTH_SHORT).show();
        }

    }

    public void playAudio(View view) {
            mediaPlayer.start();
        }

    // I know that onDestroy methods are good practice from previous coding projects/work experience.
    // Customised based on ChatGPT recommendations
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
