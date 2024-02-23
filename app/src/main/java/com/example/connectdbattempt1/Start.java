package com.example.connectdbattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Start extends AppCompatActivity {


    FloatingActionButton btnHome1;
    Button btnNext;
    TextView txtTutorial;
    ProgressBar progressBar;

    // Text change code retrieved from ChatGPT: https://chat.openai.com/share/5c6e0bbc-235f-4378-a10a-4e3191c450b5
    private String[] texts = {
            "Welcome to Hearphones.",
            "To begin, you will complete a short survey before moving on to a tutorial.",
            "The tutorial will cover how to complete two different kinds of hearing tests.",
            "You will hear a sample tone or frequency played for a few seconds.",
            "You will then submit your response. On completing the full test, you will receive your result.",
            "Lets's go!"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnHome1 = findViewById(R.id.btnHome1);
        btnNext = findViewById(R.id.btnNext);
        txtTutorial = findViewById(R.id.txtTutorial);
        progressBar = findViewById(R.id.progressBar);


        txtTutorial.setText(texts[currentIndex]);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtTutorial.setText(texts[currentIndex]);

                increaseProgressBar();
            }
        });

        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });
    }

    // Progress bar code retrieved and adapted from ChatGPT: https://chat.openai.com/share/f9884b73-dbb4-4d8b-aa4c-6864b9f277b1
    // And this website: https://abhiandroid.com/ui/progressbar#gsc.tab=0
    private void increaseProgressBar() {
        if (currentIndex < progressBar.getMax()) {
            currentIndex++;
            progressBar.setProgress(currentIndex);
        }

        if (currentIndex == 6) {
            int color = getResources().getColor(R.color.green);
            progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);

            Intent intent = new Intent(getApplicationContext(), Survey.class);
            startActivity(intent);

        }
    }








    // I know that onDestroy methods are good practice from previous coding projects/work experience.
    // Customised based on ChatGPT recommendations
    @Override
    protected void onDestroy () {
        super.onDestroy();





    }
}

