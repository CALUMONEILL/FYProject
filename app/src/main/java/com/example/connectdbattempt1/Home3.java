package com.example.connectdbattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home3 extends AppCompatActivity {
    FloatingActionButton btnHome1;
    Button btnTutorial;
    Button btnAssessments;
    Button btnHearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);

        btnHome1 = findViewById(R.id.btnHome1);
        btnTutorial = findViewById(R.id.btnTutorial);
        btnAssessments = findViewById(R.id.btnAssessments);
        btnHearing = findViewById(R.id.btnHearing);

        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Start.class);
                startActivity(intent);
            }
        });

        btnAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Assessments.class);
                startActivity(intent);
            }
        });

        btnHearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
            }
        });

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

