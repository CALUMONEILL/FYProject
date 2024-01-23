package com.example.connectdbattempt1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Test extends AppCompatActivity {

    // Defining my Media Player for the basic hearing test
    private MediaPlayer mediaPlayer;
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    public Button btnPlay1;
    public Button btnPlay2;
    Button btnHome;
    Button btnStop;

    private com.example.connectdbattempt1.HearingTestDatabaseHelper HearingTestDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, R.raw.hz250);

        btnPlay1 = findViewById(R.id.btnPlay);
        btnHome = findViewById(R.id.btnHome);
        btnStop = findViewById(R.id.btnYes);
        timerTextView = findViewById(R.id.timerTextView);

        HearingTestDatabaseHelper = new HearingTestDatabaseHelper(this);

        HearingTestDatabaseHelper.clearTable("ratings");


        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                startTimer();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                countDownTimer.cancel();
            }
        });

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            // 60 seconds countdown with 1-second intervals

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time remaining: " + secondsRemaining + " seconds");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Timer finished!");
            }
        };

        countDownTimer.start();
    }

    /*public void playAudio(View view) {
            mediaPlayer.start();

        }*/

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
