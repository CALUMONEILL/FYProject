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

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class TestYesNo extends AppCompatActivity {

    // Defining my Media Player for the basic hearing test
    MediaPlayer mediaPlayer;
    private int currentAudioIndex = 0;
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
    int yes = 0;
    int no = 0;
    private com.example.connectdbattempt1.HearingTestDatabaseHelper HearingTestDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_testyesno);

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        //mediaPlayer = MediaPlayer.create(this, R.raw.hz250);
        btnHome = findViewById(R.id.btnHome);
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        progressBar = findViewById(R.id.progressBar);

        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        HearingTestDatabaseHelper = new HearingTestDatabaseHelper(this);
        //HearingTestDatabaseHelper2.clearTable("ratings250");

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
                //yes++;
                //Toast.makeText(getApplicationContext(), "Value of yes: " + yes, Toast.LENGTH_SHORT).show();
            }

        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudio();
                increaseProgressBar();
                //no++;
                //Toast.makeText(getApplicationContext(), "Value of no: " + no, Toast.LENGTH_SHORT).show();
            }

        });
        //Toast.makeText(this, no, Toast.LENGTH_SHORT).show();
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

            Toast.makeText(this, "Audio Changed", Toast.LENGTH_SHORT).show();
        }

        private void saveDataToDatabase (String rating){
            SQLiteDatabase db = HearingTestDatabaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            //values.put("user", "Calum");
            values.put("Hz", "250");
            values.put("rating", rating);

            long newRowId = db.insert("ratings", null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Submitted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not submitted", Toast.LENGTH_SHORT).show();
            }

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
        }

}
