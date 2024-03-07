package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class TestYesNo extends AppCompatActivity {

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
    FloatingActionButton btnHome;
    Button btnYes;
    Button btnNo;
    Button btnPlay;
    ProgressBar progressBar;
    TextView txtPrompt;
    int progressValue = 0;



    private com.example.connectdbattempt1.ResponsesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_testyesno);

        // Database helper code
        dbHelper = new ResponsesDBHelper(this);
        dbHelper.clearResponsesTable("responses");


        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        btnHome = findViewById(R.id.btnHome);
        btnYes = findViewById(R.id.btnTutorial);
        btnNo = findViewById(R.id.btnNo);
        progressBar = findViewById(R.id.progressBar);
        btnPlay = findViewById(R.id.btnPlay);
        txtPrompt = findViewById(R.id.txtPrompt);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudio();
                increaseProgressBar();

                String userResponse = "Yes";

                insertResponse(userResponse);

                changePlayGreen();

            }


            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("response", userResponse);



                long newRowId = sqLiteDatabase.insert("responses", null, values);

                if (newRowId != -1) {
                    //Toast.makeText(TestYesNo.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(TestYesNo.this, "Not submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudio();
                increaseProgressBar();

                String userResponse = "No";

                insertResponse(userResponse);

                changePlayGreen();
            }

            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("response", userResponse);



                long newRowId = sqLiteDatabase.insert("responses", null, values);

                if (newRowId != -1) {
                    //Toast.makeText(TestYesNo.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(TestYesNo.this, "Not submitted", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void changePlayBlack() {
        Drawable playBackground = btnPlay.getForeground();
        int tintColor = ContextCompat.getColor(this, R.color.black);
        playBackground.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
    }

    private void changePlayGreen() {
        Drawable playBackground = btnPlay.getForeground();
        int tintColor = ContextCompat.getColor(this, R.color.green2);
        playBackground.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
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

            Intent intent = new Intent(getApplicationContext(), TestConfidence.class);
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

        //Toast.makeText(this, "Audio Changed: " + currentAudioIndex, Toast.LENGTH_SHORT).show();
    }


    public void playAudio (View view){
        mediaPlayer.start();
        changePlayBlack();
    }


    // I know that onDestroy methods are good practice from previous coding projects/work experience.
    // Customised based on ChatGPT recommendations
    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        //dbHelper.close();


    }
}

