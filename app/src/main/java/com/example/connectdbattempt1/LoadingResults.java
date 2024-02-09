package com.example.connectdbattempt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoadingResults extends AppCompatActivity {


    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_results);

        startTimer();
        }

        //Retrieved countdowntimer code from https://abhiandroid.com/ui/countdown-timer#gsc.tab=0
    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), HearingTestResults.class);
                startActivity(intent);
            }
        };

        countDownTimer.start();
    }
}




