package com.example.connectdbattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    //Image retrieved from Unsplash https://unsplash.com/photos/woman-in-brown-coat-sitting-on-rock-near-waterfalls-during-daytime-N69Hp2Kl7mY
    //Photo by <a href="https://unsplash.com/@joshhild?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Josh Hild</a> on <a href="https://unsplash.com/photos/woman-in-brown-coat-sitting-on-rock-near-waterfalls-during-daytime-N69Hp2Kl7mY?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
    //Logo created using this website https://looka.com/dashboard

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startTimer();
        }

        //Retrieved countdowntimer code from https://abhiandroid.com/ui/countdown-timer#gsc.tab=0
    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            // 5 second countdown
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), Home2.class);
                startActivity(intent);
            }
        };

        countDownTimer.start();
    }
}




