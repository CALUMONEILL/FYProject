package com.example.connectdbattempt1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestQuiz extends AppCompatActivity {

    // Defining my Media Player for the basic hearing test
    MediaPlayer mediaPlayer;
    //Change audio functionality retrieved from https://chat.openai.com/c/ea8f5988-3e35-4382-90d6-2c629db0871f
    private int currentAudioIndex = 0;
    private int currentStringIndex = 0;


    // Audio files created using the website: https://voicemaker.in/
    private int[] audioResources = {
            R.raw.team,
            R.raw.chat,
            R.raw.gaze,
            R.raw.deep,
            R.raw.thin,
            R.raw.vine,
    };


/*
* Team
* Chat
* Gaze
* Deep
* Thin
Vine
 */


    private String[] optionOne = {
            "Team",
            "Chant",
            "Graze",
            "Deep",
            "Think",
            "Vine",
            "",
    };

    private String[] optionTwo = {
            "Theme",
            "Chat",
            "Gaze",
            "Deal",
            "Then",
            "Wine",
            "",
    };

    private String[] optionThree = {
            "Tin",
            "Chap",
            "Grey",
            "Deem",
            "Thin",
            "Fine",
            "",
    };
    FloatingActionButton btnHome;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnPlay;
    ProgressBar progressBar;
    int progressValue = 0;

    private ResponsesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_testquiz);

        dbHelper = new ResponsesDBHelper(this);
        dbHelper.clearAnswersTable("answers");


        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        btnHome = findViewById(R.id.btnHome);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnPlay = findViewById(R.id.btnPlay);
        progressBar = findViewById(R.id.progressBar);

        btnOne.setText(optionOne[currentStringIndex]);
        btnTwo.setText(optionTwo[currentStringIndex]);
        btnThree.setText(optionThree[currentStringIndex]);

        //currentStringIndex = currentAudioIndex;



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });


        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                increaseProgressBar();

                String userResponse = optionOne[currentStringIndex];
                insertResponse(userResponse);
                currentStringIndex++;
                changeAudio();
                changeOptions();
                changePlayGreen();



            }


            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("answer", userResponse);



                long newRowId = sqLiteDatabase.insert("answers", null, values);

                if (newRowId != -1) {
                    //Toast.makeText(TestQuiz.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(TestQuiz.this, "Not submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                increaseProgressBar();

                String userResponse = optionTwo[currentStringIndex];
                insertResponse(userResponse);
                currentStringIndex++;
                changeAudio();
                changeOptions();
                changePlayGreen();


            }

            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("answer", userResponse);



                long newRowId = sqLiteDatabase.insert("answers", null, values);

                if (newRowId != -1) {
                    //Toast.makeText(TestQuiz.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(TestQuiz.this, "Not submitted", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                increaseProgressBar();

                String userResponse = optionThree[currentStringIndex];
                insertResponse(userResponse);
                currentStringIndex++;
                changeAudio();
                changeOptions();
                changePlayGreen();


            }

            //Adapted from ChatGPT https://chat.openai.com/share/697f768c-1520-430b-aca5-49124fe28109
            public void insertResponse(String userResponse) {

                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("answer", userResponse);



                long newRowId = sqLiteDatabase.insert("answers", null, values);

                if (newRowId != -1) {
                    //Toast.makeText(TestQuiz.this, "Submitted!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(TestQuiz.this, "Not submitted", Toast.LENGTH_SHORT).show();
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

            Intent intent = new Intent(getApplicationContext(), LoadingQuizResults.class);
            startActivity(intent);
            mediaPlayer.stop();
        }
    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/c/ea8f5988-3e35-4382-90d6-2c629db0871f
    public void changeAudio () {
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

    public void changeOptions() {
        btnOne.setText(optionOne[currentStringIndex]);
        btnTwo.setText(optionTwo[currentStringIndex]);
        btnThree.setText(optionThree[currentStringIndex]);
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

