package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class TestConfidence extends AppCompatActivity {

    // Defining my Media Player for the basic hearing test
    MediaPlayer mediaPlayer;
    //Change audio functionality retrieved from https://chat.openai.com/c/ea8f5988-3e35-4382-90d6-2c629db0871f
    private int currentAudioIndex = 0;

    // Audio files created using the website: https://www.wavtones.com/functiongenerator.php
    private int[] audioResources = {
            R.raw.hz250,
            R.raw.hz500,
            R.raw.hz1000,
            R.raw.hz2000,
            R.raw.hz4000,
            R.raw.hz8000,
    };
    Button btnHome;
    Button btnYes;
    Button btnNo;
    ProgressBar progressBar;

    RatingBar rtbRate16;
    int progressValue = 0;

    private ResponsesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test_confidence);

        // Database helper code
        dbHelper = new ResponsesDBHelper(this);
        //dbHelper.clearResponsesTable("responses");
        dbHelper.clearRatingsTable("ratings");

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        btnHome = findViewById(R.id.btnHome);
        btnYes = findViewById(R.id.btnYes);
        progressBar = findViewById(R.id.progressBar);
        rtbRate16 = findViewById(R.id.rtbRate16);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudio();
                increaseProgressBar();

                //Rating bar code adapted from https://abhiandroid.com/ui/ratingbar#gsc.tab=0
                String userResponse = String.valueOf(rtbRate16.getRating());
                Toast.makeText(getApplicationContext(), userResponse, Toast.LENGTH_LONG).show();

                insertResponse(userResponse);

            }

            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("rating", userResponse);



                long newRowId = sqLiteDatabase.insert("ratings", null, values);

                if (newRowId != -1) {
                    Toast.makeText(TestConfidence.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestConfidence.this, "Not submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/f9884b73-dbb4-4d8b-aa4c-6864b9f277b1
    // And this website: https://abhiandroid.com/ui/progressbar#gsc.tab=0
    private void increaseProgressBar() {
        if (progressValue < progressBar.getMax()) {
            progressValue++;
            progressBar.setProgress(progressValue);
        }

        if (progressValue == 6) {
            int color = getResources().getColor(R.color.green);
            progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);

            Intent intent = new Intent(getApplicationContext(), TestYesNo.class);
            startActivity(intent);
            mediaPlayer.stop();
        }
    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/c/ea8f5988-3e35-4382-90d6-2c629db0871f
    public void changeAudio (){
        mediaPlayer.stop();
        mediaPlayer.release();
        currentAudioIndex = (currentAudioIndex + 1) % audioResources.length;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + audioResources[currentAudioIndex]));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Audio Changed: " + currentAudioIndex, Toast.LENGTH_SHORT).show();
    }


    public void playAudio (View view){
        mediaPlayer.start();
    }

    // I know that onDestroy methods are good practice from previous coding projects/work experience.
    // Customised based on ChatGPT recommendations
    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }


    }
}

