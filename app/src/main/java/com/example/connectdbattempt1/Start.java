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
    private String[] texts = {"1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis.",
            "2Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis.",
            "3Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis.",
            "4Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis.",
            "5Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis.",
            "6Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis."};
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
                //currentIndex = (currentIndex + 1) % texts.length;

                txtTutorial.setText(texts[currentIndex]);

                increaseProgressBar();
            }
        });

        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Start.class);
                startActivity(intent);
            }
        });
    }

    private void increaseProgressBar() {
        if (currentIndex < progressBar.getMax()) {
            currentIndex++;
            progressBar.setProgress(currentIndex);
        }

        if (currentIndex == 6) {
            int color = getResources().getColor(R.color.green);
            progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);

            Intent intent = new Intent(getApplicationContext(), TutorialYesNo.class);
            startActivity(intent);

        }
    }

    //Retrieved and adapted from ChatGPT: https://chat.openai.com/share/f9884b73-dbb4-4d8b-aa4c-6864b9f277b1
    // And this website: https://abhiandroid.com/ui/progressbar#gsc.tab=0
    //Retrieved and adapted from ChatGPT: https://chat.openai.com/c/ea8f5988-3e35-4382-90d6-2c629db0871f





    // I know that onDestroy methods are good practice from previous coding projects/work experience.
    // Customised based on ChatGPT recommendations
    @Override
    protected void onDestroy () {
        super.onDestroy();





    }
}

