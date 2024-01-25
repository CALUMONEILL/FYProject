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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class TestYesNo extends AppCompatActivity {

    private ResultsDatabaseHelper databaseHelper;

    // Defining my Media Player for the basic hearing test
    MediaPlayer mediaPlayer;
    private int currentAudioIndex = 0;
    String userResponse = "";
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
    int progressValue = 0;

    private com.example.connectdbattempt1.ResultsDatabaseHelper ResultsDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_testyesno);

        // Database helper code
        databaseHelper = new ResultsDatabaseHelper(this);

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        btnHome = findViewById(R.id.btnHome);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        progressBar = findViewById(R.id.progressBar);

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

                userResponse = "Yes";

                //Insert data in database for responses
                insertUserResponse(currentAudioIndex, userResponse);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudio();
                increaseProgressBar();

            }
        });

    }

    private void insertUserResponse(int currentAudioIndex, String response) {
        databaseHelper.insertResponse(currentAudioIndex, response);

        Toast.makeText(this, "User Response Inserted: " + response, Toast.LENGTH_SHORT).show();



        // .insertResponse(currentAudioIndex, response);

        Toast.makeText(this, "User Response Inserted: " + response, Toast.LENGTH_SHORT).show();
    }

    private void increaseProgressBar() {
        if (progressValue < progressBar.getMax()) {
            progressValue++;
            progressBar.setProgress(progressValue);
        }

        if (progressValue == 6) {
            int color = getResources().getColor(R.color.green);
            progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);

            Intent intent = new Intent(getApplicationContext(), LoadingResults.class);
            startActivity(intent);
            mediaPlayer.stop();
        }
    }

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
            // Retrieved from ChatGPT. The following if statement pauses any audio playing from this specific button and rewinds the audio clip.
            // It plays again. If nothing is playing it plays anyway.
            // Commenting it out for now because I don't know if I even need it, start function is fine for now.
            // Might be good for multiple audios playable on one page.

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

            if (databaseHelper != null) {
                databaseHelper.close();
            }
        }

}
