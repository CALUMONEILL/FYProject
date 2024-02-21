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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class TutorialConfidence extends AppCompatActivity {

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

    ProgressBar progressBar;

    RatingBar rtbRate16;
    int progressValue = 0;

    Button btnNext2;
    Button btnNext3;
    Button btnNext4;
    View blur;
    TextView txtInfo1;
    TextView txtInfo2;
    int countNext = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_tutorial_confidence);

        // Creating the Media Player on Create. The audio file location is defined in the brackets. Retrieved from ChatGPT with some additional work to make sure the file was in the right place and the naming was correct.
        // Note: remove mp3 extension, not needed and breaks code
        mediaPlayer = MediaPlayer.create(this, audioResources[currentAudioIndex]);

        btnHome = findViewById(R.id.btnHome);
        btnYes = findViewById(R.id.btnYes);
        btnYes.setEnabled(false);
        progressBar = findViewById(R.id.progressBar);
        rtbRate16 = findViewById(R.id.rtbRate16);
        rtbRate16.setEnabled(false);
        blur = findViewById(R.id.blur);

        btnNext2 = findViewById(R.id.btnNext2);
        btnNext3 = findViewById(R.id.btnNext3);
        btnNext4 = findViewById(R.id.btnNext4);

        txtInfo1 = findViewById(R.id.txtInfo1);
        txtInfo2 = findViewById(R.id.txtInfo2);


        txtInfo1.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corners));

        txtInfo2.setText("Here, you will listen to a number of audio frequencies which cover the human hearing range");
        txtInfo2.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corners));

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInfo2.setText("Press the play button to hear the first tone...");

                countNext++;

                if (countNext == 2) {
                    blur.setVisibility(View.INVISIBLE);
                    txtInfo1.setVisibility(View.INVISIBLE);
                    txtInfo2.setVisibility(View.INVISIBLE);
                    btnNext2.setVisibility(View.INVISIBLE);
                    countNext = 0;
                }
            }
        });

        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInfo2.setText("Now, rate how well you could hear the tone out of five, and click Submit");

                countNext++;

                if (countNext == 2) {
                    blur.setVisibility(View.INVISIBLE);
                    txtInfo1.setVisibility(View.INVISIBLE);
                    txtInfo2.setVisibility(View.INVISIBLE);
                    btnNext3.setVisibility(View.INVISIBLE);
                    countNext = 0;
                    rtbRate16.setEnabled(true);
                    btnYes.setEnabled(true);
                }
            }
        });

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

                blur.setVisibility(View.VISIBLE);

                txtInfo1.setVisibility(View.VISIBLE);
                txtInfo2.setVisibility(View.VISIBLE);

                btnNext4.setVisibility(View.VISIBLE);
                txtInfo1.setText("Great!");
                txtInfo2.setText("As you would progress through the test, you can submit a rating for each tone");


            }

        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInfo2.setText("Click next to return to the homepage");

                countNext++;

                if (countNext == 2) {
                    Intent intent = new Intent(getApplicationContext(), Home3.class);
                    startActivity(intent);

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

            Intent intent = new Intent(getApplicationContext(), Stats.class);
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


    }


    public void playAudio (View view){
        mediaPlayer.start();

        blur.setVisibility(View.VISIBLE);

        txtInfo1.setVisibility(View.VISIBLE);
        txtInfo2.setVisibility(View.VISIBLE);

        btnNext3.setVisibility(View.VISIBLE);
        txtInfo1.setText("Well done!");
        txtInfo2.setText("It's O.K. if you couldn't quite hear it");
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

